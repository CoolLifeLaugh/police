package com.lhsj.police.dislock.enums;

import com.lhsj.police.core.enums.EnumAware;

public enum LockType implements EnumAware<String> {

    LOCK("阻塞式获取锁"),
    LOCK_INTERRUPTIBLY("支持线程中断的阻塞式获取锁"),
    TRY_LOCK("尝试获取锁"),
    ;

    private String desc;

    LockType(String desc) {
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
