package com.lhsj.police.limiter.throttle;

import java.util.concurrent.TimeUnit;

public class TimeWindow {

    private long     interval;
    private TimeUnit timeUnit;
    private long     threshold;

    public TimeWindow() {
    }

    public TimeWindow(long interval, TimeUnit timeUnit, long threshold) {
        this.interval = interval;
        this.timeUnit = timeUnit;
        this.threshold = threshold;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public long getThreshold() {
        return threshold;
    }

    public void setThreshold(long threshold) {
        this.threshold = threshold;
    }

    // -------- flow api -----------

    public static TimeWindow of() {
        return new TimeWindow();
    }

    public TimeWindow interval(long interval) {
        this.interval = interval;
        return this;
    }

    public TimeWindow timeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }

    public TimeWindow threshold(long threshold) {
        this.threshold = threshold;
        return this;
    }

}
