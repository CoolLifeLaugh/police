package com.lhsj.police.chain.handler;

import com.google.common.collect.Lists;
import com.lhsj.police.chain.ChainContext;

import java.util.List;

public class BizChainContext extends ChainContext {

    private List<String> handler = Lists.newArrayList();

    public List<String> getHandler() {
        return handler;
    }

    public void setHandler(List<String> handler) {
        this.handler = handler;
    }

    public void done(String handler) {
        this.handler.add(handler);
    }
}