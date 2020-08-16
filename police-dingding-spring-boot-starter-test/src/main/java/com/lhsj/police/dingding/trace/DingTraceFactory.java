package com.lhsj.police.dingding.trace;

import com.lhsj.police.core.id.ReIds;
import com.lhsj.police.core.trace.TraceFactory;
import org.springframework.stereotype.Component;

@Component("dingTraceFactory")
public class DingTraceFactory implements TraceFactory {

    @Override
    public String id() {
        return ReIds.fastUUID().toString() + "_extend";
    }

}
