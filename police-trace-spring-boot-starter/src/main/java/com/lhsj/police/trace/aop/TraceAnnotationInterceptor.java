package com.lhsj.police.trace.aop;

import com.alibaba.fastjson.JSON;
import com.lhsj.police.core.id.ReIds;
import com.lhsj.police.core.naming.AbstractName;
import com.lhsj.police.core.trace.TraceFactory;
import com.lhsj.police.trace.Traces;
import com.lhsj.police.trace.annotation.Trace;
import com.lhsj.police.trace.model.TraceLog;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.lang.NonNull;

import java.util.Optional;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class TraceAnnotationInterceptor extends AbstractName implements MethodInterceptor, BeanFactoryAware {

    private BeanFactory beanFactory;

    public TraceAnnotationInterceptor() {
        super(TraceAnnotationInterceptor.class.getCanonicalName());
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        TraceLog prevLog = Traces.getLocalTraceLog();
        TraceLog log = Traces.start();
        String prevTraceId = Traces.getLocalTraceId();
        boolean selfFirst = isBlank(prevTraceId);

        try {
            Traces.setLocalTraceLog(log);

            Trace trace = invocation.getMethod().getAnnotation(Trace.class);

            // log type & traceId
            log.type(getType(invocation, trace))
                    .traceId(getTraceId(trace, prevTraceId));

            // log request
            Object[] request = invocation.getArguments();
            Optional.of(trace).map(Trace::request).filter(e -> e)
                    .ifPresent(e -> log.request(JSON.toJSONString(request)));

            // if separate = true, log
            Optional.of(trace).map(Trace::separate).filter(e -> e)
                    .ifPresent(e -> log.costEnd().success().log());

            // log response
            Object response = invocation.proceed();
            Optional.of(trace).map(Trace::response).filter(e -> e)
                    .ifPresent(e -> log.response(JSON.toJSONString(response)));

            log.success();
            return response;
        } catch (Throwable ex) {
            log.fail().desc(ex);
            throw ex;
        } finally {
            log.cost().log();

            if (nonNull(prevLog)) {
                Traces.setLocalTraceLog(prevLog);
            } else {
                Traces.removeLocalTraceLog();
            }

            if (selfFirst) {
                Traces.removeLocalTraceId();
            }
        }
    }

    /**
     * 1. 使用线程共享的traceId
     * 2. 如果没有，则使用注解扩展的traceId，有些公司提供了全局的traceId
     * 3. 如果没有，则主动创建traceId，线程共享
     */
    private String getTraceId(Trace annotation, String prevTraceId) {
        if (isNotBlank(prevTraceId)) {
            return prevTraceId;
        }

        String traceId = ofNullable(annotation)
                .map(Trace::traceFactory)
                .filter(beanFactory::containsBean)
                .map(e -> beanFactory.getBean(e))
                .filter(e -> e instanceof TraceFactory)
                .map(e -> (TraceFactory) e)
                .map(TraceFactory::id)
                .orElse(null);

        if (isBlank(traceId)) {
            traceId = ReIds.fastUUID().toString();
        }

        Traces.setLocalTraceId(traceId);

        return traceId;
    }

    private String getType(MethodInvocation invocation, Trace trace) {
        String type = trace.type();
        if (isNotBlank(type)) {
            return type;
        }

        final String prefix = trace.prefix();
        final String fullMethodName = getFullMethodName(invocation);

        if (isNotBlank(prefix)) {
            return prefix + "_" + fullMethodName;
        } else {
            return fullMethodName;
        }
    }

    private static String getFullMethodName(MethodInvocation invocation) {
        return invocation.getMethod().getDeclaringClass().getSimpleName() + "_" + invocation.getMethod().getName();
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
