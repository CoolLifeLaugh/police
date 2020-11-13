package com.lhsj.police.chain.handler;

import com.lhsj.police.chain.ChainContext;

public interface HandlerChain<Context extends ChainContext> {

    HandlerChain<Context> addHandler(Handler<Context> handler);

    void handle(Context context);

}