package com.lhsj.police.chain.filter.interceptor;

import com.lhsj.police.chain.interceptor.DefaultInterceptorChain;
import com.lhsj.police.chain.interceptor.ExecutorContext;
import com.lhsj.police.chain.interceptor.Interceptor;

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
