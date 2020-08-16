package com.lhsj.police.responses.interceptor;

import com.lhsj.police.responses.annotation.Responses;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ResponsesAnnotationHandlerInterceptor extends HandlerInterceptorAdapter {

    private static ThreadLocal<Responses> localResponses = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        Optional.of(handler)
                .filter(e -> handler instanceof HandlerMethod)
                .map(e -> (HandlerMethod) handler)
                .map(HandlerMethod::getReturnType)
                .map(e -> e.getMethodAnnotation(Responses.class))
                .ifPresent(localResponses::set);

        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        localResponses.remove();
    }

    public static Responses getLocalResponses() {
        return localResponses.get();
    }

}
