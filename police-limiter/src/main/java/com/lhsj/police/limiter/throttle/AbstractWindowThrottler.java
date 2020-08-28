package com.lhsj.police.limiter.throttle;

import java.util.concurrent.TimeUnit;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static com.lhsj.police.core.base.Validate.isTrue;
import static com.lhsj.police.core.base.Validate.notNull;
import static com.lhsj.police.core.number.ReRandoms.nextLong;
import static java.util.Objects.nonNull;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public abstract class AbstractWindowThrottler implements WindowThrottler {
    protected TimeWindow window;

    public AbstractWindowThrottler() {
    }

    @Override
    public TimeWindow window() {
        return this.window;
    }

    public void init(TimeWindow window) {
        notNull(window, "timeWindow required but null");
        this.window = window;
    }

    @Override
    public double acquire(String key, int permits) {
        isTrue(permits > 0, "permits ({}) must be positive", permits);

        do {
            if (tryAcquire(key, permits)) {
                return 0.0D;
            }

            sleepUninterruptibly(1, MILLISECONDS);
        } while (true);
    }

    @Override
    public boolean tryAcquire(String key, int permits, long timeout, TimeUnit unit) {
        isTrue(permits > 0, "permits ({}) must be positive", permits);
        isTrue(timeout >= 0, "timeout ({}) must be positive or zero", timeout);
        isTrue(nonNull(unit), "unit must not null");

        final long originTimeout = unit.toNanos(timeout);
        long nanosTimeout = originTimeout;

        do {
            boolean success = doTryAcquire(key, permits);
            if (success) {
                return true;
            }

            if (nanosTimeout <= 0) {
                return false;
            }

            // 随机生成睡眠时间
            long nanosToWait = nextLong(originTimeout);
            sleepUninterruptibly(nanosToWait, TimeUnit.NANOSECONDS);

            nanosTimeout -= nanosToWait;
        } while (nanosTimeout > 0);

        return false;
    }

    protected abstract boolean doTryAcquire(String key, int permits);
}
