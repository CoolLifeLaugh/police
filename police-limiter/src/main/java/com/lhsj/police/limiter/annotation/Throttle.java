package com.lhsj.police.limiter.annotation;

import com.lhsj.police.limiter.enums.ThrottleType;
import com.lhsj.police.limiter.throttle.AcquireType;
import com.lhsj.police.limiter.throttle.handler.FailHandlerEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import static com.lhsj.police.limiter.enums.ThrottleType.SIMPLE_WINDOW;
import static com.lhsj.police.limiter.throttle.AcquireType.ACQUIRE;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Throttle {

    /**
     * key的表达式
     */
    String key() default "#root.targetClass.name + '::' + #root.methodName";

    /**
     * 每次申请的许可数
     */
    int permits() default 1;

    /**
     * 流控类型
     */
    ThrottleType type() default SIMPLE_WINDOW;

    /**
     * 获取许可的方式
     * <p>
     * 默认是阻塞地获取许可
     */
    AcquireType acquireType() default ACQUIRE;

    /**
     * 单位：由timeUnit()控制
     * <p>
     * type = TRY_ACQUIRE，
     * 当 timeout = 0，调用tryAcquire()
     * 当 timeout > 0，调用tryAcquire(long time, TimeUnit unit)
     */
    long timeout() default 0;

    /**
     * 间隔时间
     */
    long interval() default 1000;

    /**
     * 时间单元
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 流控阈值
     */
    long threshold() default 0;

    FailHandlerEnum failHandler() default FailHandlerEnum.DEFAULT_FAIL_HANDLER;
}
