package com.lhsj.police.dingding.request;

import java.io.Serializable;
import java.util.List;

public class At implements Serializable {

    private List<String> atMobiles;
    private boolean      isAtAll;

    public At() {
    }

    public At(List<String> atMobiles, boolean isAtAll) {
        this.atMobiles = atMobiles;
        this.isAtAll = isAtAll;
    }

    public List<String> getAtMobiles() {
        return atMobiles;
    }

    public void setAtMobiles(List<String> atMobiles) {
        this.atMobiles = atMobiles;
    }

    public boolean isAtAll() {
        return isAtAll;
    }

    public void setAtAll(boolean atAll) {
        isAtAll = atAll;
    }

    // --------- flow api ----------

    public static At of() {
        return new At();
    }

    public At atMobiles(List<String> atMobiles) {
        this.atMobiles = atMobiles;
        return this;
    }

    public At atAll(boolean atAll) {
        isAtAll = atAll;
        return this;
    }

}
