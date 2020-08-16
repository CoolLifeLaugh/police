package com.lhsj.police.responses.handler;

import com.lhsj.police.core.response.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ResponseHandler {

    default Response<?> handleSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Object result) {
        return null;
    }

    default Response<?> handleError(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Throwable ex) {
        return null;
    }

}
