package com.lhsj.police.limiter.redis.aop;

import com.lhsj.police.limiter.annotation.Throttle;
import com.lhsj.police.limiter.aop.AbstractLimiterInterceptor;
import com.lhsj.police.limiter.redis.throttle.RedisThrottler;
import com.lhsj.police.limiter.throttle.Throttler;
import com.lhsj.police.limiter.throttle.TimeWindow;
import org.springframework.data.redis.core.RedisTemplate;

import static com.lhsj.police.limiter.enums.ThrottleType.REDIS_SIMPLE_WINDOW;

public class LimiterRedisAnnotationInterceptor extends AbstractLimiterInterceptor {

    private RedisTemplate<Object, Object> redisTemplate;

    public LimiterRedisAnnotationInterceptor(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected Throttler doCreateThrottler(Throttle annotation, String key) {
        if (annotation.type() != REDIS_SIMPLE_WINDOW) {
            throw new UnsupportedOperationException("unsupported throttle type, require redis limiter");
        }

        RedisThrottler throttler = new RedisThrottler(redisTemplate);

        TimeWindow window = TimeWindow.of()
                .interval(annotation.interval())
                .timeUnit(annotation.timeUnit())
                .threshold(annotation.threshold());

        throttler.init(window);

        return throttler;
    }
}
