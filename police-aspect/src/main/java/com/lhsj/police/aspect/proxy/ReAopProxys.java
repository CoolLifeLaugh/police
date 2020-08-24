package com.lhsj.police.aspect.proxy;

import org.springframework.aop.framework.AdvisedSupport;

import java.lang.reflect.Field;

import static com.lhsj.police.aspect.proxy.ReProxys.getCglibTarget;
import static com.lhsj.police.aspect.proxy.ReProxys.getJdkDynamicTarget;
import static com.lhsj.police.core.base.Validate.isTrue;
import static java.util.Objects.nonNull;
import static org.springframework.aop.support.AopUtils.isAopProxy;
import static org.springframework.aop.support.AopUtils.isCglibProxy;
import static org.springframework.aop.support.AopUtils.isJdkDynamicProxy;

/**
 * 参考：https://www.iteye.com/blog/jinnianshilongnian-1613222
 */
public class ReAopProxys {

    private final static String AOP_ADVICE_FIELD = "advised";

    public static Object getTarget(Object proxy) throws Exception {
        if (!isAopProxy(proxy)) {
            return proxy;
        }

        if (isJdkDynamicProxy(proxy)) {
            return getJdkDynamicProxyTargetObject(proxy);
        }

        if (isCglibProxy(proxy)) {
            return getCglibProxyTargetObject(proxy);
        }

        return null;
    }

    public static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Object dynamicAdvisedInterceptor = getCglibTarget(proxy);

        isTrue(nonNull(dynamicAdvisedInterceptor), "dynamicAdvisedInterceptor null");

        return getProxyTargetObject(dynamicAdvisedInterceptor);
    }

    public static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Object aopProxy = getJdkDynamicTarget(proxy);

        isTrue(nonNull(aopProxy), "aopProxy null");

        return getProxyTargetObject(aopProxy);
    }

    private static Object getProxyTargetObject(Object proxy) throws Exception {
        Field advised = proxy.getClass().getDeclaredField(AOP_ADVICE_FIELD);
        advised.setAccessible(true);
        return ((AdvisedSupport) advised.get(proxy)).getTargetSource().getTarget();
    }
}
