package com.lhsj.police.responses.handler;

import com.lhsj.police.core.response.Response;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ResponseHandler {

    default Response<?> handleSuccess(@NonNull HttpServletRequest request,
                                      @NonNull HttpServletResponse response,
                                      @Nullable Object result) {
        return Response.ofSuccess().data(result);
    }

    default Response<?> handleError(@NonNull HttpServletRequest request,
                                    @Nullable HttpServletResponse response,
                                    @NonNull Throwable ex) {
        return Response.ofError().msg(ex.getMessage());
    }

}
