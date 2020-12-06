package com.lhsj.police.chain.interceptor;

public interface InterceptorChain<Context extends ExecutorContext> {

    void addExecutor(Executor<Context> executor);

    void addInterceptor(Interceptor<Context> interceptor);

    void execute(Context context);
}
