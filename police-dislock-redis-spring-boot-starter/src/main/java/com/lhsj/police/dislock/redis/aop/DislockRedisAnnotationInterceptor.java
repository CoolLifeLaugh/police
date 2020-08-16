package com.lhsj.police.dislock.redis.aop;

import com.google.common.collect.Lists;
import com.lhsj.police.core.base.ReValidates;
import com.lhsj.police.core.naming.AbstractName;
import com.lhsj.police.dislock.annotation.Dislock;
import com.lhsj.police.dislock.enums.LockType;
import com.lhsj.police.dislock.redis.result.LockResult;
import com.lhsj.police.expression.CacheOperationExpressionEvaluator;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.locks.Lock;

import static java.util.Objects.nonNull;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.apache.commons.lang3.ObjectUtils.allNotNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class DislockRedisAnnotationInterceptor extends AbstractName implements MethodInterceptor, BeanFactoryAware {

    private BeanFactory                       beanFactory;
    private RedisLockRegistry                 redisLockRegistry;
    private CacheOperationExpressionEvaluator evaluator = new CacheOperationExpressionEvaluator();

    public DislockRedisAnnotationInterceptor() {
        super(DislockRedisAnnotationInterceptor.class.getCanonicalName());
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Lock lock = null;
        LockResult result = null;
        try {
            Dislock dislock = invocation.getMethod().getAnnotation(Dislock.class);

            String lockKey = createKey(invocation, dislock);
            ReValidates.isTrue(isNotBlank(dislock.key()), "dislock key blank");

            lock = redisLockRegistry.obtain(lockKey);
            ReValidates.isTrue(nonNull(lock), "lock obtain null, lockKey is " + lockKey);

            result = lockIfNeeded(lock, dislock);
            ReValidates.isTrue(result.getSuccess(), "lock fail, lockKey is " + lockKey);

            return invocation.proceed();
        } finally {
            unlockIfNeeded(lock, result);
        }
    }

    private LockResult lockIfNeeded(Lock lock, Dislock dislock) throws InterruptedException {
        LockType type = dislock.type();

        if (Objects.equals(type, LockType.LOCK)) {

            lock.lock();
            return LockResult.of().success();

        } else if (Objects.equals(type, LockType.LOCK_INTERRUPTIBLY)) {

            lock.lockInterruptibly();
            return LockResult.of().success();

        } else {

            long timeout = dislock.timeout();
            if (timeout > 0) {
                return LockResult.of().success(lock.tryLock(timeout, MILLISECONDS));
            } else {
                return LockResult.of().success(lock.tryLock());
            }
        }
    }

    private void unlockIfNeeded(Lock lock, LockResult result) {
        if (!allNotNull(lock, result)) {
            return;
        }

        if (!result.getSuccess()) {
            return;
        }

        lock.unlock();
    }

    private String createKey(MethodInvocation invocation, Dislock dislock) {
        Method method = invocation.getMethod();
        Object[] args = invocation.getArguments();
        Object target = invocation.getThis();
        Class<?> targetClass = invocation.getThis().getClass();

        EvaluationContext context = evaluator.createEvaluationContext(Lists.newArrayList(), method, args, target, targetClass, beanFactory);

        AnnotatedElementKey methodCacheKey = new AnnotatedElementKey(method, targetClass);

        return (String) evaluator.key(dislock.key(), methodCacheKey, context);
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        this.redisLockRegistry = (RedisLockRegistry) beanFactory.getBean("redisLockRegistry");
    }
}
