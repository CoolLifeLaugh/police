package com.lhsj.police.chain.filter;

import com.lhsj.police.chain.Request;
import com.lhsj.police.chain.Response;

public interface FilterChain<Req extends Request, Resp extends Response> {

    void doFilter(Req request, Resp response);

    FilterChain<Req, Resp> addFilter(Filter<Req, Resp> filter);

    default void reuse() {
    }

    default void release() {
    }
}