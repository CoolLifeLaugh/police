package com.lhsj.police.trace.annotation;

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
public @interface Trace {

    /**
     * 前缀，加在type前面
     */
    String prefix() default "";

    /**
     * 核心参数，日志类型
     */
    String type() default "";

    /**
     * 打印入参
     */
    boolean request() default false;

    /**
     * 打印出参
     */
    boolean response() default false;

    /**
     * 自定义traceId，可以通过bean的方式透传
     */
    String traceFactory() default "policeTraceFactory";

    /**
     * 是否分开记录日志
     * <p>
     * AOP是环绕记录，先开启的日志记录，如果只打印一条，一般是后置打印；
     * 如果配置为true，则开始打印一条，执行目标方法，拿到response后，再打印一条日志
     */
    boolean separate() default false;

    /**
     * true，会从配置中读取keys，如果包含type，就打印
     */
    boolean simpleFilter() default false;

    /**
     * true, 从aspectJ表达式匹配method，匹配就打印
     */
    boolean expressionFilter() default false;

    /**
     * 打印比率，值的取值范围[10, 100, 1000, 10000], 表示比率的分母，例如100表示百分之一
     * <p>
     * 默认1，表示百分之百，值不在范围内，取默认值
     */
    int rateFilter() default 1;
}
