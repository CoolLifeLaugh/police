package com.lhsj.police.responses.handler;

import com.lhsj.police.core.response.Response;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("policeResponseHandler")
public class PoliceResponseHandler implements ResponseHandler {

    @Override
    public Response<?> handleError(@NonNull HttpServletRequest request,
                                   @Nullable HttpServletResponse response,
                                   @NonNull Throwable ex) {
        return Response.ofError().msg("extend handler: " + ex.getMessage());
    }

}
