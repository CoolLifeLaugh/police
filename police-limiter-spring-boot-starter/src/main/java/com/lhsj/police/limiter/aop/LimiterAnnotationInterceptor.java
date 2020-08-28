package com.lhsj.police.limiter.aop;

import com.lhsj.police.limiter.annotation.Throttle;
import com.lhsj.police.limiter.throttle.SimpleWindowThrottler;
import com.lhsj.police.limiter.throttle.Throttler;
import com.lhsj.police.limiter.throttle.TimeWindow;

import static com.lhsj.police.limiter.enums.ThrottleType.SIMPLE_WINDOW;

public class LimiterAnnotationInterceptor extends AbstractLimiterInterceptor {

    @Override
    protected Throttler doCreateThrottler(Throttle annotation, String key) {
        if (annotation.type() != SIMPLE_WINDOW) {
            throw new UnsupportedOperationException("unsupported throttle type, require common limiter");
        }

        SimpleWindowThrottler throttler = new SimpleWindowThrottler();

        TimeWindow window = TimeWindow.of()
                .interval(annotation.interval())
                .timeUnit(annotation.timeUnit())
                .threshold(annotation.threshold());

        throttler.init(window);

        return throttler;
    }
}
