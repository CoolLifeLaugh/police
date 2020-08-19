package com.lhsj.police.mock.annotation;

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
public @interface Mock {

    /**
     * 命中mock的key的表达式
     */
    String key() default "";

    /**
     * mock数据存放的路径的表达式
     */
    String path() default "";

    /**
     * 是否从远程获取mock数据
     */
    boolean remote() default false;

    /**
     * 从远程获取数据的URL，请求方式为GET
     * <p>
     * 最终请求的URL格式：url() + (?或&) + key=key()
     */
    String url() default "";
}
