package com.lhsj.police.limiter.aop;

import com.lhsj.police.aspect.invocation.ReInvocations;
import com.lhsj.police.core.base.ReValidates;
import com.lhsj.police.core.log.ReLogs;
import com.lhsj.police.expression.CacheOperationExpressionEvaluator;
import com.lhsj.police.limiter.annotation.Throttle;
import com.lhsj.police.limiter.exception.LimitException;
import com.lhsj.police.limiter.throttle.Throttler;
import com.lhsj.police.limiter.throttle.handler.FailHandler;
import com.lhsj.police.limiter.throttle.handler.FailHandlerEnum;
import com.lhsj.police.limiter.throttle.handler.ThrottleAttribute;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.lhsj.police.aspect.invocation.ReInvocations.getTargetMethod;
import static com.lhsj.police.core.base.Validate.isTrue;
import static com.lhsj.police.core.collection.ReMaps.newConcurrentHashMap;
import static com.lhsj.police.limiter.throttle.AcquireType.ACQUIRE;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@SuppressWarnings("all")
public abstract class AbstractLimiterInterceptor implements MethodInterceptor, BeanFactoryAware {

    protected ConcurrentHashMap<String, Throttler> throttlerMap = newConcurrentHashMap();
    protected CacheOperationExpressionEvaluator    evaluator    = new CacheOperationExpressionEvaluator();
    protected BeanFactory                          beanFactory;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        final Method method = getTargetMethod(invocation);
        if (isNull(method)) {
            return invocation.proceed();
        }

        Throttle annotation = AnnotationUtils.findAnnotation(invocation.getMethod(), Throttle.class);
        if (isNull(annotation)) {
            return invocation.proceed();
        }

        validate(annotation);

        String key = parse(invocation, annotation.key());
        isTrue(isNotBlank(key), "throttle key blank");

        Throttler throttler = null;
        try {
            throttler = getThrottler(annotation, key);

            synchronized (throttler) {
                if (annotation.acquireType() == ACQUIRE) {
                    throttler.acquire(key, annotation.permits());
                } else {
                    boolean acquired = false;

                    if (annotation.timeout() <= 0) {
                        acquired = throttler.tryAcquire(key);
                    } else {
                        acquired = throttler.tryAcquire(key, annotation.timeout(), annotation.timeUnit());
                    }

                    ReValidates.isTrue(acquired, new LimitException("tryAcquire failed, key " + key));
                }
            }

            return invocation.proceed();
        } catch (Throwable ex) {
            return handleFailIfNeeded(invocation, annotation, key, throttler, ex);
        }
    }

    private Object handleFailIfNeeded(MethodInvocation invocation,
                                      Throttle annotation,
                                      String key,
                                      Throttler throttler,
                                      Throwable ex) throws Throwable {
        FailHandler handler = Optional.of(annotation)
                .map(Throttle::failHandler)
                .map(FailHandlerEnum::code)
                .orElse(null);

        if (isNull(handler)) {
            throw ex;
        }

        ThrottleAttribute attribute = ThrottleAttribute.of()
                .annotation(annotation)
                .key(key)
                .throttler(throttler)
                .target(invocation.getThis())
                .method(getTargetMethod(invocation))
                .args(invocation.getArguments())
                .throwable(ex);

        return handler.process(attribute, () -> ReInvocations.proceedUnchecked(invocation));
    }

    private Throttler getThrottler(Throttle annotation, String key) {
        Throttler throttler = throttlerMap.get(key);

        if (isNull(throttler)) {
            throttler = doCreateThrottler(annotation, key);
            throttlerMap.putIfAbsent(key, throttler);
        }

        return throttler;
    }

    protected abstract Throttler doCreateThrottler(Throttle annotation, String key);

    private String parse(MethodInvocation invocation, String expression) {
        try {
            Method method = invocation.getMethod();
            Object[] args = invocation.getArguments();
            Object target = invocation.getThis();
            Class<?> targetClass = invocation.getThis().getClass();
            EvaluationContext context = evaluator.createEvaluationContext(method, args, target, targetClass, beanFactory);
            AnnotatedElementKey methodCacheKey = new AnnotatedElementKey(method, targetClass);

            return (String) evaluator.key(expression, methodCacheKey, context);
        } catch (Throwable e) {
            ReLogs.warn(getClass().getSimpleName(), e);
            return null;
        }
    }

    private void validate(Throttle annotation) {
        isTrue(isNotBlank(annotation.key()), "annotation key blank");
        isTrue(annotation.permits() > 0, "annotation permits invalid");
        isTrue(annotation.timeout() >= 0, "annotation timeout invalid");
        isTrue(annotation.interval() > 0, "annotation interval invalid");
        isTrue(annotation.threshold() >= 0, "annotation threshold invalid");
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
