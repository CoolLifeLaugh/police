package com.lhsj.police.chain.interceptor;

public interface Interceptor<Context> {

    default boolean preHandle(Context context) {
        return true;
    }

    default void postHandle(Context context) {
    }

    default void afterCompletion(Context context, Throwable ex) {
    }
}
