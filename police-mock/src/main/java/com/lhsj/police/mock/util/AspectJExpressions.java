package com.lhsj.police.mock.util;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.lhsj.police.core.reflect.ReReflections.getAllDeclaredMethods;
import static java.util.stream.Collectors.toList;

public final class AspectJExpressions {

    private AspectJExpressions() {
    }

    public static boolean match(String expression, Class<?> clazz, Method method) {
        AspectJExpressionPointcut apc = new AspectJExpressionPointcut();
        apc.setExpression(expression);
        return apc.matches(method, clazz);
    }

    public static List<String> getMatchMethod(String expression, Class<?> clazz) {
        AspectJExpressionPointcut apc = new AspectJExpressionPointcut();
        apc.setExpression(expression);

        return Arrays.stream(getAllDeclaredMethods(clazz))
                .filter(Objects::nonNull)
                .filter(e -> apc.matches(e, clazz))
                .map(Method::toString)
                .collect(toList());
    }
}
