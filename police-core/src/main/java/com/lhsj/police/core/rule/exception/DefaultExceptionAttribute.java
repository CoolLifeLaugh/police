package com.lhsj.police.core.rule.exception;

import com.lhsj.police.core.exception.IgnoreException;

public class DefaultExceptionAttribute implements ExceptionAttribute {

    @Override
    public boolean match(Throwable ex) {
        return ex instanceof IgnoreException;
    }

}
