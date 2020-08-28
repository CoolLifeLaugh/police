package com.lhsj.police.limiter.throttle;

import com.lhsj.police.core.enums.EnumAware;

public enum AcquireType implements EnumAware<String> {

    ACQUIRE("阻塞式获取许可"),
    TRY_ACQUIRE("尝试获取许可"),
    ;

    private String desc;

    AcquireType(String desc) {
        this.desc = desc;
    }

    @Override
    public String code() {
        return this.name().toUpperCase();
    }

    @Override
    public String desc() {
        return this.desc;
    }
}