package com.lhsj.police.core.exception;

public class StateException extends AbstractCodeException {

    public StateException() {
    }

    public StateException(String message) {
        super(message);
    }

    public StateException(String code, String message) {
        super(code, message);
    }

    public StateException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
