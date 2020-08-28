package com.lhsj.police.limiter.throttle.handler;

import com.lhsj.police.core.base.ReValidates;
import com.lhsj.police.limiter.annotation.Throttle;
import com.lhsj.police.limiter.exception.LimitException;
import com.lhsj.police.limiter.throttle.Throttler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

import static com.lhsj.police.core.base.ReExceptions.unchecked;
import static java.util.Objects.nonNull;

public interface FailHandler {

    Logger logger = LoggerFactory.getLogger(FailHandler.class);

    Object process(ThrottleAttribute attribute, Supplier<?> supplier);

    FailHandler DEFAULT_FAIL_HANDLER = (attribute, supplier) -> {
        if (nonNull(attribute.getThrowable())) {
            unchecked(attribute.getThrowable());
        }
        
        throw new LimitException("failed to acquire permit, key = " + attribute.getKey());
    };

    FailHandler IGNORE_FAIL_HANDLER = (attribute, supplier) -> {
        logger.warn("failed to acquire permit, key = {}" + attribute.getKey());
        return supplier.get();
    };

    FailHandler BLOCKING_FAIL_HANDLER = (attribute, supplier) -> {
        Throttler throttler = attribute.getThrottler();
        Throttle annotation = attribute.getAnnotation();
        String key = attribute.getKey();

        ReValidates.notNull(throttler, "throttler null");
        ReValidates.notNull(annotation, "annotation null");
        ReValidates.notNull(key, "key null");

        throttler.acquire(key, annotation.permits());

        return supplier.get();
    };

}
