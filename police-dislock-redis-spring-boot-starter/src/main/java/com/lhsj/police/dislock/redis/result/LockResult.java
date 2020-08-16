package com.lhsj.police.dislock.redis.result;

import java.io.Serializable;

@SuppressWarnings("unused")
public class LockResult implements Serializable {

    private boolean success;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    // ------ flow api -------

    public static LockResult of() {
        return new LockResult();
    }

    public LockResult success(boolean success) {
        this.success = success;
        return this;
    }

    public LockResult success() {
        this.success = true;
        return this;
    }

    public LockResult fail() {
        this.success = false;
        return this;
    }
}
