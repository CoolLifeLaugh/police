package com.lhsj.police.exceptions.annotation;

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
public @interface ExceptionResolver {

    /**
     * 捕捉的异常类型
     */
    Class<Throwable>[] types() default {Throwable.class};

    /**
     * 日志的name
     */
    String logger() default "";

    /**
     * ExceptionHandler接口的实现类
     */
    String handler() default "policeExceptionHandler";

}
