# police-chain

包含FilterChain, HandlerChain

## FilterChain

从Tomcat剥离出来的责任链组件。

```java
public class FilterChainApp {

    public static void main(String[] args) {
        DefaultFilterChain<Request, Response> chain = new DefaultFilterChain<>();
        Request request = new Request();
        Response response = new Response();

        chain
                .addFilter((req, resp, filterChain) -> {
                    System.out.println("filter1");
                    chain.doFilter(req, resp);
                })
                .addFilter((req, resp, filterChain) -> {
                    System.out.println("filter2");
                    chain.doFilter(req, resp);
                })
                .addFilter((req, resp, filterChain) -> {
                    System.out.println("filter3");
                    chain.doFilter(req, resp);
                })
        ;

        chain.doFilter(request, response);
    }

}
```

## HandlerChain

业务项目中，使用更多的是这一种责任链。

```java
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
```