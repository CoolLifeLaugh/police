package com.lhsj.police.aspect.proxy;

import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

import static com.lhsj.police.core.base.ReValidates.isTrue;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

public class ReProxys {

    private final static String JAVA_PROXY_TARGET_FIELD  = "h";
    private final static String CGLIB_PROXY_TARGET_FIELD = "CGLIB$CALLBACK_0";

    public static Object getTarget(Object proxy) {
        isTrue(nonNull(proxy), "proxy null");

        if (isJdkDynamicProxy(proxy)) {
            return getJdkDynamicTarget(proxy);
        }

        if (isCglibProxy(proxy)) {
            return getCglibTarget(proxy);
        }

        return null;
    }

    public static Object getJdkDynamicTarget(Object proxy) {
        isTrue(nonNull(proxy), "proxy null");

        try {
            Field h = proxy.getClass().getSuperclass().getDeclaredField(JAVA_PROXY_TARGET_FIELD);
            h.setAccessible(true);
            return h.get(proxy);
        } catch (Throwable ex) {
            return null;
        }
    }

    public static Object getCglibTarget(Object proxy) {
        isTrue(nonNull(proxy), "proxy null");

        try {
            Field h = proxy.getClass().getDeclaredField(CGLIB_PROXY_TARGET_FIELD);
            h.setAccessible(true);
            return h.get(proxy);
        } catch (Throwable ex) {
            return null;
        }
    }

    public static boolean isJdkDynamicProxy(Object proxy) {
        return ofNullable(proxy)
                .map(Object::getClass)
                .map(Proxy::isProxyClass)
                .orElse(false);
    }

    public static boolean isCglibProxy(Object proxy) {
        return ofNullable(proxy)
                .map(Object::getClass)
                .map(Class::getName)
                .map(e -> e.contains(ClassUtils.CGLIB_CLASS_SEPARATOR))
                .orElse(false);
    }
}
