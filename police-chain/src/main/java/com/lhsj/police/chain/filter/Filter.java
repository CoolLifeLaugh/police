package com.lhsj.police.chain.filter;

import com.lhsj.police.chain.Request;
import com.lhsj.police.chain.Response;

public interface Filter<Req extends Request, Resp extends Response> {

    void doFilter(Req request, Resp response, FilterChain<Req, Resp> chain);

}