package com.lhsj.police.limiter.throttle;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MICROSECONDS;

@SuppressWarnings("unused")
public interface Throttler {

    default double acquire(String key) {
        return acquire(key, 1);
    }

    double acquire(String key, int permits);

    default boolean tryAcquire(String key, Duration timeout) {
        return tryAcquire(key, 1, timeout.toNanos(), TimeUnit.NANOSECONDS);
    }

    default boolean tryAcquire(String key, long timeout, TimeUnit unit) {
        return tryAcquire(key, 1, timeout, unit);
    }

    default boolean tryAcquire(String key, int permits) {
        return tryAcquire(key, permits, 0, MICROSECONDS);
    }

    default boolean tryAcquire(String key) {
        return tryAcquire(key, 1, 0, MICROSECONDS);
    }

    default boolean tryAcquire(String key, int permits, Duration timeout) {
        return tryAcquire(key, permits, timeout.toNanos(), TimeUnit.NANOSECONDS);
    }

    boolean tryAcquire(String key, int permits, long timeout, TimeUnit unit);
}
