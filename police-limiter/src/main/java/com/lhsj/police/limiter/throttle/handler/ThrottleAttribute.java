package com.lhsj.police.limiter.throttle.handler;

import com.lhsj.police.limiter.annotation.Throttle;
import com.lhsj.police.limiter.throttle.Throttler;

import java.lang.reflect.Method;

public class ThrottleAttribute {

    private Throttle  annotation;
    private String    key;
    private Throttler throttler;
    private Object    target;
    private Method    method;
    private Object[]  args;
    private Throwable throwable;

    public ThrottleAttribute() {
    }

    public ThrottleAttribute(Throttle annotation, Throttler throttler) {
        this.annotation = annotation;
        this.throttler = throttler;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Throttle getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Throttle annotation) {
        this.annotation = annotation;
    }

    public Throttler getThrottler() {
        return throttler;
    }

    public void setThrottler(Throttler throttler) {
        this.throttler = throttler;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    // --------- flow api ---------

    public static ThrottleAttribute of() {
        return new ThrottleAttribute();
    }

    public ThrottleAttribute key(String key) {
        this.key = key;
        return this;
    }

    public ThrottleAttribute annotation(Throttle annotation) {
        this.annotation = annotation;
        return this;
    }

    public ThrottleAttribute throttler(Throttler throttler) {
        this.throttler = throttler;
        return this;
    }

    public ThrottleAttribute target(Object target) {
        this.target = target;
        return this;
    }

    public ThrottleAttribute method(Method method) {
        this.method = method;
        return this;
    }

    public ThrottleAttribute args(Object[] args) {
        this.args = args;
        return this;
    }

    public ThrottleAttribute throwable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }
}
