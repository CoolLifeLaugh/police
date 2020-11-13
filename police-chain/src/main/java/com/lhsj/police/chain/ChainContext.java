package com.lhsj.police.chain;

import java.io.Serializable;

/**
 * 基础上下文
 */
public class ChainContext implements Serializable {
    /**
     * 处理结果
     */
    private ChainResult result = new ChainResult();
    /**
     * 结束标记
     * <p>
     * 如果为true，后续的节点，不会执行
     */
    private boolean     done   = false;

    public ChainResult getResult() {
        return result;
    }

    public void setResult(ChainResult result) {
        this.result = result;
    }

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}