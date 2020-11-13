package com.lhsj.police.chain.handler;

import com.lhsj.police.core.json.ReJsons;

public class HandlerChainApp {

    public static void main(String[] args) {
        DefaultHandlerChain<BizChainContext> chain = new DefaultHandlerChain<>();
        BizChainContext context = new BizChainContext();

        chain.addHandler(new Handler<BizChainContext>() {
            @Override
            public void preHandle(BizChainContext context) {
                System.out.println("handler1 preHandle");
            }

            @Override
            public void handle(BizChainContext context) {
                context.done("handler1");
            }
        });

        chain.addHandler(new Handler<BizChainContext>() {
            @Override
            public void handle(BizChainContext context) {
                context.done("handler2");
                context.setDone(true);
            }

            @Override
            public void postHandle(BizChainContext bizChainContext) {
                System.out.println("handler2 postHandle");
            }
        });

        chain.handle(context);

        System.out.println("context handler: " + ReJsons.obj2String(context.getHandler()));
        System.out.println("context done: " + ReJsons.obj2String(context.getDone()));
        System.out.println("context result: " + ReJsons.obj2String(context.getResult()));

    }

}
