package com.lhsj.police.limiter.exception;

public class LimitException extends RuntimeException {

    public LimitException() {
        super();
    }

    public LimitException(String message) {
        super(message);
    }

    public LimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public LimitException(Throwable cause) {
        super(cause);
    }

    protected LimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
