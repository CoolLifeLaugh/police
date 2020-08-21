package com.lhsj.police.expression;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;

import java.lang.reflect.Method;

public class ExpressionApp {

    private CacheOperationExpressionEvaluator evaluator = new CacheOperationExpressionEvaluator();
    private BeanFactory                       beanFactory;

    private String createKey(MethodInvocation invocation, String template) {
        Method method = invocation.getMethod();
        Object[] args = invocation.getArguments();
        Object target = invocation.getThis();
        Class<?> targetClass = invocation.getThis().getClass();
        EvaluationContext context = evaluator.createEvaluationContext(method, args, target, targetClass, beanFactory);
        AnnotatedElementKey methodCacheKey = new AnnotatedElementKey(method, targetClass);

        return (String) evaluator.key(template, methodCacheKey, context);
    }

}
