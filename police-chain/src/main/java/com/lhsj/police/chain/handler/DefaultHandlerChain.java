package com.lhsj.police.chain.handler;

import com.google.common.collect.Lists;
import com.lhsj.police.chain.ChainContext;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class DefaultHandlerChain<Context extends ChainContext> implements HandlerChain<Context> {

    private final List<Handler<Context>> chain = Lists.newArrayList();

    @Override
    public HandlerChain<Context> addHandler(Handler<Context> handler) {
        chain.add(handler);
        return this;
    }

    public HandlerChain<Context> addHeadHandler(Handler<Context> handler) {
        chain.add(0, handler);
        return this;
    }

    public HandlerChain<Context> addTailHandler(Handler<Context> handler) {
        chain.add(handler);
        return this;
    }

    @Override
    public void handle(Context context) {
        if (CollectionUtils.isEmpty(chain)) {
            return;
        }

        for (Handler<Context> handler : chain) {
            if (!handler.supports(context)) {
                continue;
            }

            if (context.getDone() && !handler.ignoreDone(context)) {
                break;
            }

            handler.preHandle(context);

            handler.handle(context);

            handler.postHandle(context);
        }
    }
}