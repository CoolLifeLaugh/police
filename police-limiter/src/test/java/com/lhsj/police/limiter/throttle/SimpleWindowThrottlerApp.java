package com.lhsj.police.limiter.throttle;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class SimpleWindowThrottlerApp {

    public static void main(String[] args) {
        testTryAcquire_key_timeout_unit();
    }

    private static void testTryAcquire_key_permits() {
        TimeWindow window = TimeWindow.of().interval(1).timeUnit(TimeUnit.SECONDS).threshold(10);
        SimpleWindowThrottler throttler = new SimpleWindowThrottler();
        throttler.init(window);

        for (int i = 0; i < 100; i++) {
            System.out.println(i + "_" + throttler.tryAcquire("", 1));
            if (i % 20 == 0) {
                LockSupport.parkNanos(1_000_000_000L);
            }
        }
    }

    private static void testTryAcquire_key_timeout_unit() {
        TimeWindow window = TimeWindow.of().interval(1).timeUnit(TimeUnit.SECONDS).threshold(10);
        SimpleWindowThrottler throttler = new SimpleWindowThrottler();
        throttler.init(window);

        for (int i = 0; i < 100; i++) {
            System.out.println(i + "_" + throttler.tryAcquire("", 100, TimeUnit.MILLISECONDS));
        }
    }

}
