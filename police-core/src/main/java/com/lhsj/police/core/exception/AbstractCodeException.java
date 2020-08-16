package com.lhsj.police.core.exception;

import com.google.common.base.Joiner;

public abstract class AbstractCodeException extends RuntimeException {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AbstractCodeException() {
    }

    public AbstractCodeException(String message) {
        super(message);
    }

    public AbstractCodeException(String code, String message) {
        super(message);
        this.code = code;
    }

    public AbstractCodeException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    @Override
    public String toString() {
        String s = getClass().getName();
        String message = getLocalizedMessage();
        return Joiner.on("_").useForNull("-").join(s, code, message);
    }
}
