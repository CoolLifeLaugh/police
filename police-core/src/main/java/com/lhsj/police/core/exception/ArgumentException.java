package com.lhsj.police.core.exception;

public class ArgumentException extends AbstractCodeException {
    public ArgumentException() {
    }

    public ArgumentException(String message) {
        super(message);
    }

    public ArgumentException(String code, String message) {
        super(code, message);
    }

    public ArgumentException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
