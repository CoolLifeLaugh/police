package com.lhsj.police.dingding.aop;

import com.lhsj.police.core.log.ReLogs;
import com.lhsj.police.core.naming.AbstractName;
import com.lhsj.police.core.net.ReNets;
import com.lhsj.police.core.rule.exception.RuleExceptions;
import com.lhsj.police.core.text.ReStrings;
import com.lhsj.police.core.time.ReDateFormats;
import com.lhsj.police.core.trace.TraceFactory;
import com.lhsj.police.dingding.annotation.DingDing;
import com.lhsj.police.dingding.base.DingDings;
import com.lhsj.police.dingding.request.DingTextRequest;
import com.lhsj.police.dingding.sign.Signs;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.lang.NonNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class DingDingAnnotationInterceptor extends AbstractName implements MethodInterceptor, BeanFactoryAware {

    private BeanFactory beanFactory;

    public DingDingAnnotationInterceptor() {
        super(DingDingAnnotationInterceptor.class.getCanonicalName());
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } catch (Throwable e) {
            try {
                sendDingDingIfNeeded(invocation, e);
            } catch (Throwable ex) {
                ReLogs.warn(getClass().getName(), ex);
            }
            throw e;
        }
    }

    private void sendDingDingIfNeeded(MethodInvocation invocation, Throwable ex) {
        Optional.of(invocation)
                .map(MethodInvocation::getMethod)
                .map(t -> t.getAnnotation(DingDing.class))
                .filter(t -> sendForIfNeeded(t, ex))
                .map(t -> ImmutablePair.of(t, createRequest(ex, t)))
                .map(t -> ImmutablePair.of(parseWebhook(t.left), t.right))
                .filter(t -> isNotBlank(t.left))
                .ifPresent(t -> DingDings.sendText(t.left, t.right));
    }

    private boolean sendForIfNeeded(DingDing annotation, Throwable ex) {
        return ofNullable(annotation)
                .map(DingDing::sendFor)
                .map(e -> RuleExceptions.match(e, ex))
                .orElse(false);
    }

    private DingTextRequest createRequest(Throwable e, DingDing annotation) {
        return DingTextRequest.of()
                .content(buildContent(e, annotation))
                .atMobiles(Arrays.stream(annotation.atMobiles()).collect(toList()))
                .atAll(annotation.atAll());
    }

    private String parseWebhook(DingDing annotation) {
        String webhook = Optional.of(annotation)
                .map(DingDing::webhook)
                .filter(StringUtils::isNotBlank)
                .filter(e -> e.startsWith("${"))
                .map(e -> ((ConfigurableBeanFactory) beanFactory).resolveEmbeddedValue(e))
                .orElse(annotation.webhook());

        webhook = signIfNeeded(annotation, webhook);

        return webhook;
    }

    /**
     * 如果注解的sign不为空，则拼接webhook
     * <p>
     * https://oapi.dingtalk.com/robot/send?access_token=XXXXXX&timestamp=XXX&sign=XXX
     */
    private String signIfNeeded(DingDing annotation, String webhook) {
        if (isBlank(annotation.sign())) {
            return webhook;
        }

        String secret = annotation.sign();
        Long timestamp = System.currentTimeMillis();
        String signSecret = Signs.sign(timestamp, secret);
        return ReStrings.format("{}&timestamp={}&sign={}", webhook, timestamp, signSecret);
    }

    private String buildContent(Throwable ex, DingDing annotation) {
        StringBuilder sb = new StringBuilder();

        if (isNotBlank(annotation.keyword())) {
            sb.append("keyword: ").append(annotation.keyword()).append("\r");
        }

        sb.append("date: ").append(ReDateFormats.formatDateTime(new Date())).append("\r");
        sb.append("ip: ").append(ReNets.getLocalAddress().getHostAddress()).append("\r");

        String traceId = getTraceId(annotation);
        if (isNotBlank(traceId)) {
            sb.append("traceId: ").append(traceId).append("\r");
        }

        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        ex.printStackTrace(pw);

        String[] exceptionStackTrace = ofNullable(writer.toString())
                .map(t -> t.split("\n"))
                .orElse(new String[0]);
        for (int i = 0, len = exceptionStackTrace.length; i < len; i++) {
            if (i > 5) {
                break;
            }
            sb.append(exceptionStackTrace[i]).append("\r");
        }

        pw.flush();
        pw.close();

        return sb.toString();
    }

    private String getTraceId(DingDing annotation) {
        return ofNullable(annotation)
                .map(DingDing::traceFactory)
                .filter(beanFactory::containsBean)
                .map(e -> beanFactory.getBean(e))
                .filter(e -> e instanceof TraceFactory)
                .map(e -> (TraceFactory) e)
                .map(TraceFactory::id)
                .orElse(null);
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
