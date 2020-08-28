package com.lhsj.police.limiter.throttle;

public class SimpleWindowThrottler extends AbstractWindowThrottler {
    private long lastReqTime = System.currentTimeMillis();
    private long counter     = 0L;
    private long millisWindow;

    public SimpleWindowThrottler() {
    }

    @Override
    public void init(TimeWindow window) {
        super.init(window);
        this.millisWindow = window.getTimeUnit().toMillis(window.getInterval());
    }

    @Override
    public boolean doTryAcquire(String key, int permits) {
        // 阈值不大于0，说明不用限流
        if (window.getThreshold() <= 0) {
            return true;
        }

        long now = System.currentTimeMillis();

        // 如果当前时间与上一次访问的时间差，超过了时间窗口的长度，则重置时间窗口，变成新的时间窗口
        if (now - lastReqTime > millisWindow) {
            counter = 0L;
            lastReqTime = now;
        }

        if (counter + (long) permits <= window.getThreshold()) {
            counter += permits;
            return true;
        } else {
            return false;
        }
    }
}
