package com.lhsj.police.trace;

import com.lhsj.police.core.id.ReIds;
import com.lhsj.police.core.trace.TraceFactory;
import org.springframework.stereotype.Component;

@Component("policeTraceFactory")
public class DefaultTraceFactory implements TraceFactory {

    @Override
    public String id() {
        return ReIds.fastUUID().toString() + "_extend";
    }

}
