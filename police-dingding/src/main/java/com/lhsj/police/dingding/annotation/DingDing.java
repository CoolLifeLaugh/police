package com.lhsj.police.dingding.annotation;

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
public @interface DingDing {

    /**
     * 发送钉钉消息的URL
     */
    String webhook() default "";

    /**
     * 是否at所有人
     */
    boolean atAll() default false;

    /**
     * at的手机号
     */
    String[] atMobiles() default {};

    /**
     * 系统发生什么类型的异常时，才会发送钉钉消息
     */
    Class<? extends Throwable>[] sendFor() default {Throwable.class};

    /**
     * 钉钉消息，如果需要携带traceId，可以通过bean的方式透传
     */
    String traceFactory() default "dingTraceFactory";

    /**
     * 钉钉消息加签密钥
     */
    String sign() default "";

    /**
     * 钉钉消息关键词，多个关键词用任意字符隔开
     */
    String keyword() default "";
}
