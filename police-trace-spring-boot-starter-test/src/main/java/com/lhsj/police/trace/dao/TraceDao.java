package com.lhsj.police.trace.dao;

import com.lhsj.police.core.constant.Constants;
import com.lhsj.police.core.exception.BizException;
import com.lhsj.police.trace.annotation.Trace;
import org.springframework.stereotype.Component;

@Component
public class TraceDao {

    @Trace(request = true, response = true)
    public boolean simple(String traceParam) {
        return true;
    }

    @Trace(request = true, response = true)
    public boolean exception(String traceParam) {
        if (true) {
            throw new RuntimeException("error happened");
        }
        return true;
    }

    @Trace(request = true, response = true)
    public boolean codeException(String traceParam) {
        if (true) {
            throw new BizException(Constants.CODE_ERROR, "error happened");
        }
        return true;
    }
}
