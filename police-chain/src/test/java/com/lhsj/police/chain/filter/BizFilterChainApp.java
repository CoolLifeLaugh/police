package com.lhsj.police.chain.filter;

import com.lhsj.police.chain.Request;
import com.lhsj.police.chain.Response;

public class BizFilterChainApp {

    public static void main(String[] args) {
        DefaultFilterChain<BizRequest, BizResponse<String>> chain = new DefaultFilterChain<>();
        BizRequest request = new BizRequest();
        request.setBizType("buy");
        BizResponse<String> response = new BizResponse<>();

        chain
                .addFilter((req, resp, filterChain) -> {
                    System.out.println("filter1, biz = " + req.getBizType());
                    chain.doFilter(req, resp);
                })
                .addFilter((req, resp, filterChain) -> {
                    System.out.println("filter2");
                    System.out.println("filter2, biz = " + req.getBizType());
                    chain.doFilter(req, resp);
                })
                .addFilter((req, resp, filterChain) -> {
                    System.out.println("filter3");
                    System.out.println("filter3, biz = " + req.getBizType());
                    chain.doFilter(req, resp);
                })
        ;

        chain.doFilter(request, response);

    }

    private static class BizRequest extends Request {
        private String bizType;

        public String getBizType() {
            return bizType;
        }

        public void setBizType(String bizType) {
            this.bizType = bizType;
        }
    }

    private static class BizResponse<T> extends Response {
        private T data;

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }
}