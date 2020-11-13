package com.lhsj.police.chain;

import java.io.Serializable;

@SuppressWarnings("unused")
public class ChainResult implements Serializable {

    private static final long serialVersionUID = -4630081528055019768L;

    /**
     * 是否成功
     */
    private boolean   success = true;
    /**
     * 错误消息
     */
    private String    message;
    /**
     * 错误异常
     */
    private Throwable throwable;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    // flow api

    public static ChainResult of() {
        return new ChainResult();
    }

    public ChainResult success(Boolean success) {
        this.success = success;
        return this;
    }

    public ChainResult message(String message) {
        this.message = message;
        return this;
    }

    public ChainResult throwable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }
}
