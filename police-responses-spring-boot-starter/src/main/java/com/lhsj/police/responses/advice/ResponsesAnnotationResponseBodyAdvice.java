package com.lhsj.police.responses.advice;

import com.lhsj.police.core.exception.AbstractCodeException;
import com.lhsj.police.core.response.Response;
import com.lhsj.police.responses.annotation.Responses;
import com.lhsj.police.responses.handler.ResponseHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.lhsj.police.responses.interceptor.ResponsesAnnotationHandlerInterceptor.getLocalResponses;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class ResponsesAnnotationResponseBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice implements BeanFactoryAware {

    @Resource
    private BeanFactory beanFactory;

    @Override
    public boolean supports(@NonNull MethodParameter returnType,
                            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return super.supports(returnType, converterType) && nonNull(getLocalResponses());
    }

    @Override
    protected void beforeBodyWriteInternal(@NonNull MappingJacksonValue mappingJacksonValue,
                                           @NonNull MediaType mediaType,
                                           @NonNull MethodParameter methodParameter,
                                           @NonNull ServerHttpRequest serverHttpRequest,
                                           @NonNull ServerHttpResponse serverHttpResponse) {
        Responses annotation = getLocalResponses();
        if (isNull(annotation)) {
            return;
        }

        // 如果禁用了rapper，直接return
        if (!annotation.rapper()) {
            return;
        }

        ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
        ServletServerHttpResponse response = (ServletServerHttpResponse) serverHttpResponse;
        handleSuccess(request.getServletRequest(), response.getServletResponse(), mappingJacksonValue, annotation);
    }

    private void handleSuccess(HttpServletRequest request,
                               HttpServletResponse response,
                               MappingJacksonValue mappingJacksonValue,
                               Responses annotation) {
        ResponseHandler handler = getResponseHandler(annotation);
        Object result = mappingJacksonValue.getValue();

        if (isNull(handler) || !annotation.handleIfSuccess()) {
            result = Response.ofSuccess().data(result);
        } else {
            result = handler.handleSuccess(request, response, result);
        }

        mappingJacksonValue.setValue(result);
    }

    @ResponseBody
    @ExceptionHandler({Throwable.class})
    private Response<?> responsesAnnotationExceptionHandler(WebRequest request, Throwable e) {
        Responses annotation = getLocalResponses();
        if (isNull(annotation)) {
            return null;
        }

        // 如果禁用了rapper，直接return
        if (!annotation.rapper()) {
            return null;
        }

        return handleError(request, annotation, e);
    }

    private Response<?> handleError(WebRequest webRequest, Responses annotation, Throwable ex) {
        ResponseHandler handler = getResponseHandler(annotation);

        if (isNull(handler) || !annotation.handleIfError()) {
            if (ex instanceof AbstractCodeException) {
                AbstractCodeException cex = (AbstractCodeException) ex;
                return Response.of().error().code(cex.getCode()).msg(cex.getMessage());
            } else {
                return Response.ofError().msg(ex.getMessage());
            }
        } else {
            ServletWebRequest servletWebRequest = (ServletWebRequest) webRequest;
            return handler.handleError(servletWebRequest.getRequest(), servletWebRequest.getResponse(), ex);
        }
    }

    private ResponseHandler getResponseHandler(Responses annotation) {
        return (ResponseHandler) ofNullable(annotation)
                .map(Responses::handler)
                .filter(StringUtils::isNotBlank)
                .filter(beanFactory::containsBean)
                .map(beanFactory::getBean)
                .filter(e -> e instanceof ResponseHandler)
                .orElse(null);
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

}
