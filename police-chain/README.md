# police-chain

包含FilterChain, HandlerChain, InterceptorChain。

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

## InterceptorChain

Executor只有一个，且只执行一次，但是Interceptor有多个，且支持环绕扩展。参考Spring的HandlerExecutionChain实现。

```java
public class InterceptorApp {

    public static void main(String[] args) {
        DefaultInterceptorChain<UserContext> chain = new DefaultInterceptorChain<>();

        chain.addExecutor(userContext -> {
            System.out.println("executor, userId = " + userContext.getUserId());
        });

        chain.addInterceptor(new Interceptor<UserContext>() {
            @Override
            public boolean preHandle(UserContext userContext) {
                System.out.println("preHandle, userId = " + userContext.getUserId());
                return true;
            }

            @Override
            public void postHandle(UserContext userContext) {
                System.out.println("postHandle, userId = " + userContext.getUserId());
            }

            @Override
            public void afterCompletion(UserContext userContext, Throwable ex) {
                System.out.println("afterCompletion, userId = " + userContext.getUserId());
            }
        });

        chain.execute(new UserContext(1L));

    }

    private static class UserContext extends ExecutorContext {
        private Long userId;

        public UserContext(Long userId) {
            this.userId = userId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }

}
```
