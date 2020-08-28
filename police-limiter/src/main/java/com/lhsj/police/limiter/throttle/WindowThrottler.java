package com.lhsj.police.limiter.throttle;

public interface WindowThrottler extends Throttler {

    /**
     * 获取时间窗口
     */
    TimeWindow window();

    /**
     * 初始化节流器
     */
    void init(TimeWindow timeWindow);

}
