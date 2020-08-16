package com.lhsj.police.responses.handler;

import com.lhsj.police.core.response.Response;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("policeResponseHandler")
public class PoliceResponseHandler implements ResponseHandler {

    @Override
    public Response<?> handleError(HttpServletRequest request, HttpServletResponse response, Throwable ex) {
        return Response.ofError().msg("extend handler: " + ex.getMessage());
    }

}
