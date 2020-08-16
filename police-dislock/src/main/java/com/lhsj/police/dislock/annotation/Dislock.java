package com.lhsj.police.dislock.annotation;

import com.lhsj.police.dislock.enums.LockType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Dislock {

    String key() default "";

    /**
     * 加锁类型
     */
    LockType type() default LockType.LOCK;

    /**
     * type = TRY_LOCK，
     * 当 timeout = 0，调用tryLock()
     * 当 timeout > 0，调用tryLock(long time, TimeUnit unit)
     */
    long timeout() default 0;
}
