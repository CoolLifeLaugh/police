package com.lhsj.police.mock.aop;

import com.google.common.collect.Lists;
import com.lhsj.police.core.json.ReJsons;
import com.lhsj.police.core.log.ReLogs;
import com.lhsj.police.core.net.RestClient;
import com.lhsj.police.core.response.Response;
import com.lhsj.police.expression.CacheOperationExpressionEvaluator;
import com.lhsj.police.mock.annotation.Mock;
import com.lhsj.police.mock.configuration.MockProperties;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.lhsj.police.aspect.invocation.ReInvocations.getTargetMethod;
import static com.lhsj.police.core.base.Validate.isTrue;
import static com.lhsj.police.core.io.ReFiles.isFileExists;
import static com.lhsj.police.core.json.ReJsons.obj2String;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.ObjectUtils.allNotNull;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class MockAnnotationInterceptor implements MethodInterceptor, BeanFactoryAware {

    private CacheOperationExpressionEvaluator evaluator = new CacheOperationExpressionEvaluator();
    private MockProperties                    properties;
    private BeanFactory                       beanFactory;

    public MockAnnotationInterceptor(MockProperties properties) {
        this.properties = properties;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            final Method method = getTargetMethod(invocation);
            if (isNull(method)) {
                return invocation.proceed();
            }

            Class<?> returnType = method.getReturnType();
            if (returnType.getCanonicalName().equals("void")) {
                return null;
            }

            Mock annotation = method.getAnnotation(Mock.class);
            if (isNull(annotation)) {
                return invocation.proceed();
            }

            String key = parse(invocation, annotation.key());
            isTrue(isNotBlank(key), "mock key blank");

            String resultJson;
            if (annotation.remote()) {
                resultJson = remoteMock(annotation, key);
            } else {
                resultJson = localMock(invocation, annotation, key);
            }

            isTrue(isNotBlank(resultJson), "mock result is blank, key = {}, remote = {}", key, annotation.remote());

            return ReJsons.string2Obj(resultJson, (Class<?>) method.getGenericReturnType());
        } catch (Throwable ex) {
            ReLogs.warn(getClass().getSimpleName(), ex);
            throw ex;
        }
    }

    private String remoteMock(Mock annotation, String key) throws MalformedURLException {
        String url = createUrl(annotation.url(), key);

        Response<?> response = RestClient.instance().get(url, Response.class);

        ReLogs.info("remote mock, url = {}, response = {}", url, obj2String(response));

        return (String) ofNullable(response).map(Response::getData).orElse(null);
    }

    private String createUrl(String url, String key) throws MalformedURLException {
        String query = new URL(url).getQuery();

        if (isBlank(query)) {
            url = url + "?key=" + key;
        } else {
            url = url + "&key=" + key;
        }

        return url;
    }

    private String localMock(MethodInvocation invocation, Mock annotation, String key) throws IOException {
        String path = parse(invocation, annotation.path());
        isTrue(isFileExists(path), "mock path blank");

        return getMockResult(path, key);
    }

    private String parse(MethodInvocation invocation, String expression) {
        try {
            Method method = invocation.getMethod();
            Object[] args = invocation.getArguments();
            Object target = invocation.getThis();
            Class<?> targetClass = invocation.getThis().getClass();
            EvaluationContext context = evaluator.createEvaluationContext(Lists.newArrayList(), method, args, target, targetClass, beanFactory);
            AnnotatedElementKey methodCacheKey = new AnnotatedElementKey(method, targetClass);

            return (String) evaluator.key(expression, methodCacheKey, context);
        } catch (Throwable e) {
            ReLogs.warn(getClass().getSimpleName(), e);
            return null;
        }
    }

    private String getMockResult(String path, String key) throws IOException {
        return Files.readAllLines(Paths.get(path))
                .stream()
                .filter(StringUtils::isNotBlank)
                .map(e -> e.split(properties.getSeparator()))
                .filter(e -> e.length == 2)
                .filter(e -> allNotNull(e[0], e[1]))
                .filter(e -> StringUtils.equals(e[0].trim(), key))
                .map(e -> e[1].trim())
                .filter(StringUtils::isNotBlank)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
