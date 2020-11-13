package com.lhsj.police.chain.filter;

import com.lhsj.police.chain.Request;
import com.lhsj.police.chain.Response;

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