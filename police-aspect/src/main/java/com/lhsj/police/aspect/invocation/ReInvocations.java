package com.lhsj.police.aspect.invocation;

import com.lhsj.police.core.base.ReExceptions;
import org.aopalliance.intercept.Joinpoint;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.function.Function;

import static com.lhsj.police.core.base.ReValidates.isTrue;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

public final class ReInvocations {

    public static Class<?> getTargetClass(MethodInvocation invocation) {
        return ofNullable(invocation)
                .map(Joinpoint::getThis)
                .map(AopUtils::getTargetClass)
                .orElse(null);
    }

    public static Method getTargetMethod(MethodInvocation invocation) {
        Function<Class<?>, Method> mapper = clazz -> ClassUtils.getMostSpecificMethod(invocation.getMethod(), clazz);

        return ofNullable(invocation)
                .map(ReInvocations::getTargetClass)
                .map(mapper)
                .map(BridgeMethodResolver::findBridgedMethod)
                .orElse(null);
    }

    public static <A extends Annotation> A getTargetAnnotation(MethodInvocation invocation, Class<A> annotationType) {
        isTrue(nonNull(annotationType), "annotationType null");

        Function<Method, A> mapper = method -> method.getAnnotation(annotationType);

        return ofNullable(invocation)
                .map(ReInvocations::getTargetMethod)
                .map(mapper)
                .orElse(null);
    }

    public static Object proceedUnchecked(MethodInvocation invocation) {
        try {
            return invocation.proceed();
        } catch (Throwable ex) {
            ReExceptions.unchecked(ex);
        }

        throw new UnsupportedOperationException("wont do this in theory");
    }
}
