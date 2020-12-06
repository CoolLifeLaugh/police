package com.lhsj.police.chain.interceptor;

import com.lhsj.police.chain.ChainResult;

public class ExecutorContext {

    private ChainResult result = new ChainResult();

    public ChainResult getResult() {
        return result;
    }

    public void setResult(ChainResult result) {
        this.result = result;
    }
}
