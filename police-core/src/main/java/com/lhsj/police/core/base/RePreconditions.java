package com.lhsj.police.core.base;

import com.lhsj.police.core.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public final class RePreconditions {
    private RePreconditions() {
    }

    @Deprecated
    public static void check(boolean expression, @Nullable RuntimeException e) {
        if (!expression) {
            throw e;
        }
    }

}
