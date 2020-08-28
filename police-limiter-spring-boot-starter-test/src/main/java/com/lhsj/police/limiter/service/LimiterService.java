package com.lhsj.police.limiter.service;

import com.lhsj.police.limiter.annotation.Throttle;
import org.springframework.stereotype.Service;

@Service
public class LimiterService {

    @Throttle()
    public boolean simple() {
        return true;
    }

    @Throttle(timeout = 100, threshold = 5)
    public boolean timeout() {
        return true;
    }

    @Throttle(threshold = 10)
    public boolean threshold() {
        return true;
    }
}
