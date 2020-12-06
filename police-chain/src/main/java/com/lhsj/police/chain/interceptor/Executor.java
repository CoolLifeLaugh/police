package com.lhsj.police.chain.interceptor;

public interface Executor<Context> {

    void execute(Context context);

}
