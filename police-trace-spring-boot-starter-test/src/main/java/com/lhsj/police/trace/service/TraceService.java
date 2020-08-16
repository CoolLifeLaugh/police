package com.lhsj.police.trace.service;

import com.lhsj.police.trace.Traces;
import com.lhsj.police.trace.annotation.Trace;
import com.lhsj.police.trace.dao.TraceDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TraceService {

    @Resource
    private TraceDao traceDao;

    @Trace(prefix = "tt", request = true, response = true, separate = true)
    public boolean simple(String traceParam) {
        Traces.key("[simple param={}]", traceParam);
        return traceDao.simple(traceParam);
    }

    @Trace(request = true, response = true)
    public boolean exception(String traceParam) {
        Traces.key("[exception param={}]", traceParam);
        return traceDao.exception(traceParam);
    }

    @Trace(request = true, response = true)
    public boolean codeException(String traceParam) {
        Traces.key("[exception param={}]", traceParam);
        return traceDao.codeException(traceParam);
    }
}
