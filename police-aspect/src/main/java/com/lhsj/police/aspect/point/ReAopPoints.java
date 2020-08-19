package com.lhsj.police.aspect.point;

import com.lhsj.police.core.reflect.ReReflections;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public final class ReAopPoints {

    private ReAopPoints() {
    }

    /**
     * 校验class的method，匹配指定aspectj表达式
     */
    public static boolean match(String expression, Class<?> clazz, Method method) {
        AspectJExpressionPointcut apc = new AspectJExpressionPointcut();
        apc.setExpression(expression);
        return apc.matches(method, clazz);
    }

    /**
     * 获取class的所有匹配指定aspectj表达式的method
     */
    public static List<String> getMatchMethod(String expression, Class<?> clazz) {
        AspectJExpressionPointcut apc = new AspectJExpressionPointcut();
        apc.setExpression(expression);

        return Arrays.stream(ReReflections.getAllDeclaredMethods(clazz))
                .filter(Objects::nonNull)
                .filter(e -> apc.matches(e, clazz))
                .map(Method::toString)
                .collect(toList());
    }

}
