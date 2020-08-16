package com.lhsj.police.exceptions.aop;

import com.google.common.base.Defaults;
import com.google.common.collect.Maps;
import com.lhsj.police.core.naming.AbstractName;
import com.lhsj.police.core.rule.exception.RuleExceptions;
import com.lhsj.police.exceptions.annotation.ExceptionResolver;
import com.lhsj.police.exceptions.handler.ExceptionHandler;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

public class ExceptionResolverAnnotationInterceptor extends AbstractName implements MethodInterceptor, BeanFactoryAware {

    private       BeanFactory         beanFactory;
    private final Map<String, Logger> loggerMap = Maps.newConcurrentMap();

    public ExceptionResolverAnnotationInterceptor() {
        super(ExceptionResolverAnnotationInterceptor.class.getCanonicalName());
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } catch (Throwable ex) {
            ExceptionResolver annotation = invocation.getMethod().getAnnotation(ExceptionResolver.class);

            if (!needResolve(annotation, ex)) {
                throw ex;
            }

            handleIfNeeded(annotation, ex);

            logIfNeeded(annotation, ex);

            return Defaults.defaultValue(invocation.getMethod().getReturnType());
        }
    }

    private boolean needResolve(ExceptionResolver annotation, Throwable ex) {
        return ofNullable(annotation)
                .map(ExceptionResolver::types)
                .map(e -> RuleExceptions.match(e, ex))
                .orElse(false);
    }

    private void handleIfNeeded(ExceptionResolver annotation, Throwable ex) {
        ofNullable(annotation)
                .map(ExceptionResolver::handler)
                .filter(beanFactory::containsBean)
                .map(e -> beanFactory.getBean(e))
                .filter(e -> e instanceof ExceptionHandler)
                .map(e -> (ExceptionHandler) e)
                .ifPresent(e -> e.handle(ex));
    }

    private void logIfNeeded(ExceptionResolver annotation, Throwable ex) {
        Optional.of(annotation.logger())
                .filter(StringUtils::isNotBlank)
                .map(this::getLogger)
                .ifPresent(e -> e.warn(getClass().getName(), ex));
    }

    private Logger getLogger(String loggerName) {
        Logger logger = loggerMap.get(loggerName);
        if (isNull(logger)) {
            logger = LoggerFactory.getLogger(loggerName);
            if (isNull(logger)) {
                return null;
            }
            loggerMap.putIfAbsent(loggerName, logger);
            logger = loggerMap.get(loggerName);
        }
        return logger;
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
