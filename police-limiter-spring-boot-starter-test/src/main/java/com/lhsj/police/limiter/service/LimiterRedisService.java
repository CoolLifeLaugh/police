package com.lhsj.police.limiter.service;

import com.lhsj.police.limiter.annotation.Throttle;
import org.springframework.stereotype.Service;

import static com.lhsj.police.limiter.enums.ThrottleType.REDIS_SIMPLE_WINDOW;

@Service
public class LimiterRedisService {

    @Throttle(type = REDIS_SIMPLE_WINDOW, threshold = 10)
    public boolean simple() {
        return true;
    }

    @Throttle(type = REDIS_SIMPLE_WINDOW, timeout = 100, threshold = 5)
    public boolean timeout() {
        return true;
    }

    @Throttle(type = REDIS_SIMPLE_WINDOW, threshold = 100)
    public boolean threshold() {
        return true;
    }
}
