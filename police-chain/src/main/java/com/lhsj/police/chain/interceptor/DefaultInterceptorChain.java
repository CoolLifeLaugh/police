package com.lhsj.police.chain.interceptor;

import com.lhsj.police.core.collection.ReArrays;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultInterceptorChain<Context extends ExecutorContext> implements InterceptorChain<Context> {

    private static final Log logger = LogFactory.getLog(DefaultInterceptorChain.class);

    private Executor<Context> executor;
    private Interceptor<Context>[] interceptors;
    private List<Interceptor<Context>> interceptorList;
    private int interceptorIndex = -1;

    @Override
    public void addExecutor(Executor<Context> executor) {
        this.executor = executor;
    }

    @Override
    public void addInterceptor(Interceptor<Context> interceptor) {
        initInterceptorList().add(interceptor);
    }

    private List<Interceptor<Context>> initInterceptorList() {
        if (this.interceptorList == null) {
            this.interceptorList = new ArrayList<>();
            if (this.interceptors != null) {
                interceptorList = Arrays.asList(interceptors);
            }
        }
        this.interceptors = null;
        return this.interceptorList;
    }

    @Override
    public void execute(Context context) {
        try {
            if (!applyPreHandle(context)) {
                return;
            }
            executor.execute(context);
            applyPostHandle(context);
        } catch (Throwable ex) {
            triggerAfterCompletion(context, ex);
        }
    }

    @SuppressWarnings("unchecked")
    public Interceptor<Context>[] getInterceptors() {
        if (this.interceptors == null && this.interceptorList != null) {
            this.interceptors = this.interceptorList.toArray(new Interceptor[0]);
        }
        return this.interceptors;
    }

    boolean applyPreHandle(Context context) {
        Interceptor<Context>[] interceptors = getInterceptors();
        if (ReArrays.isNotEmpty(interceptors)) {
            for (int i = 0; i < interceptors.length; i++) {
                Interceptor<Context> interceptor = interceptors[i];
                if (!interceptor.preHandle(context)) {
                    triggerAfterCompletion(context, null);
                    return false;
                }
                this.interceptorIndex = i;
            }
        }
        return true;
    }

    void applyPostHandle(Context context) {
        Interceptor<Context>[] interceptors = getInterceptors();
        if (ReArrays.isNotEmpty(interceptors)) {
            for (int i = interceptors.length - 1; i >= 0; i--) {
                Interceptor<Context> interceptor = interceptors[i];
                interceptor.postHandle(context);
            }
        }
    }

    void triggerAfterCompletion(Context context, Throwable ex) {
        Interceptor<Context>[] interceptors = getInterceptors();
        if (ReArrays.isNotEmpty(interceptors)) {
            for (int i = this.interceptorIndex; i >= 0; i--) {
                Interceptor<Context> interceptor = interceptors[i];
                try {
                    interceptor.afterCompletion(context, ex);
                } catch (Throwable ex2) {
                    logger.error("Interceptor.afterCompletion threw exception", ex2);
                }
            }
        }
    }
}
