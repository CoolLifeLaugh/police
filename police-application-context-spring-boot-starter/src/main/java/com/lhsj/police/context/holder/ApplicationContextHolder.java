package com.lhsj.police.context.holder;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Map;

import static com.lhsj.police.core.base.ReValidates.notNull;
import static java.util.Objects.isNull;

public class ApplicationContextHolder {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getContext() {
        notNull(applicationContext, "applicationContext null");
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) getContext().getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return getContext().getBean(requiredType);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return getContext().getBean(name, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType, Object... args) {
        return getContext().getBean(requiredType, args);
    }

    public static <T> Map<String, T> getBeansOfType(@Nullable Class<T> type) {
        return getContext().getBeansOfType(type);
    }

    public static boolean containsBean(String name) {
        return getContext().containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return getContext().isSingleton(name);
    }

    public static boolean isTypeMatch(String name, ResolvableType typeToMatch) {
        return getContext().isTypeMatch(name, typeToMatch);
    }

    public static boolean isTypeMatch(String name, Class<?> typeToMatch) {
        return getContext().isTypeMatch(name, typeToMatch);
    }

    public static Class<?> getType(String name) {
        return getContext().getType(name);
    }

    public static Class<?> getType(String name, boolean allowFactoryBeanInit) {
        return getContext().getType(name, allowFactoryBeanInit);
    }

    public static String[] getAliases(String name) {
        return getContext().getAliases(name);
    }

    public static void destroy() {
        applicationContext = null;
    }

    public void setContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    @Override
    public String toString() {
        return isNull(applicationContext) ? "applicationContext null" : applicationContext.toString();
    }
}
