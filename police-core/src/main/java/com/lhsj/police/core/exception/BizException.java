package com.lhsj.police.core.exception;

public class BizException extends AbstractCodeException {
    public BizException() {
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String code, String message) {
        super(code, message);
    }

    public BizException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
