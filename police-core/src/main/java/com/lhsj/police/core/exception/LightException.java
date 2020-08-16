package com.lhsj.police.core.exception;

public class LightException extends RuntimeException {

    public LightException() {
        super();
    }

    public LightException(String message) {
        super(message);
    }

    public LightException(String message, Throwable cause) {
        super(message, cause);
    }

    public LightException(Throwable cause) {
        super(cause);
    }

    protected LightException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}
