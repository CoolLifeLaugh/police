package com.lhsj.police.responses.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Controller & Resource，只需要关心业务方法的返回值，不用手写response逻辑
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Responses {

    /**
     * 是否包装
     */
    boolean rapper() default true;

    /**
     * 扩展：成功时，调用handler处理
     */
    boolean handleIfSuccess() default false;

    /**
     * 扩展：异常时，调用handler处理
     */
    boolean handleIfError() default false;

    /**
     * ResponseHandler接口的实现类，如果没有，则使用默认的ResponseHandler
     * <p>
     * 原理：以返回值做为beanName，获取BeanFactory的ResponseHandler
     */
    String handler() default "policeResponseHandler";
}
