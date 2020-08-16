package com.lhsj.police.core.exception;

public class HandlerException extends RuntimeException {

    private Object data;

    public HandlerException(Object data) {
        this.data = data;
    }

    public HandlerException(String message, Object data) {
        super(message);
        this.data = data;
    }

    public HandlerException(String message, Throwable cause, Object data) {
        super(message, cause);
        this.data = data;
    }

    public HandlerException(Throwable cause, Object data) {
        super(cause);
        this.data = data;
    }

    public HandlerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object data) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
