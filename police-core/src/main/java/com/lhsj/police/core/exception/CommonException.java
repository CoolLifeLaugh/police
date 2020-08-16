package com.lhsj.police.core.exception;

public class CommonException extends AbstractCodeException {
    public CommonException() {
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String code, String message) {
        super(code, message);
    }

    public CommonException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
