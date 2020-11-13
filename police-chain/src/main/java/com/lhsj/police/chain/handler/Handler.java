package com.lhsj.police.chain.handler;

/**
 * 处理器接口
 */
public interface Handler<Context> {

    /**
     * 前置处理
     */
    default void preHandle(Context context) {
    }

    /**
     * 处理
     */
    void handle(Context context);

    /**
     * 后置处理
     */
    default void postHandle(Context context) {
    }

    /**
     * 是否忽略done
     */
    default boolean ignoreDone(Context context) {
        return false;
    }

}