package com.lhsj.police.core.rule.exception;

import java.io.Serializable;

public interface ExceptionAttribute extends Serializable {

    boolean match(Throwable ex);
}
