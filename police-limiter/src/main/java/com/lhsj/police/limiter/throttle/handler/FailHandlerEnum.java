package com.lhsj.police.limiter.throttle.handler;

import com.lhsj.police.core.enums.EnumAware;

public enum FailHandlerEnum implements EnumAware<FailHandler> {

    DEFAULT_FAIL_HANDLER(FailHandler.DEFAULT_FAIL_HANDLER),
    IGNORE_FAIL_HANDLER(FailHandler.IGNORE_FAIL_HANDLER),
    BLOCKING_FAIL_HANDLER(FailHandler.BLOCKING_FAIL_HANDLER),
    ;

    private FailHandler failHandler;

    FailHandlerEnum(FailHandler failHandler) {
        this.failHandler = failHandler;
    }

    @Override
    public FailHandler code() {
        return this.failHandler;
    }

    @Override
    public String desc() {
        return this.failHandler.getClass().getName();
    }
}
