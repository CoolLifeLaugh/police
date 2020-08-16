package com.lhsj.police.core.exception;

public class IgnoreException extends AbstractCodeException {
    public IgnoreException() {
        super();
    }

    public IgnoreException(String message) {
        super(message);
    }

    public IgnoreException(String code, String message) {
        super(code, message);
    }

    public IgnoreException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
