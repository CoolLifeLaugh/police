package com.lhsj.police.limiter.redis.throttle;

import com.lhsj.police.limiter.throttle.AbstractWindowThrottler;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.Objects;

import static com.google.common.collect.Lists.newArrayList;

public class RedisThrottler extends AbstractWindowThrottler {

    private RedisTemplate<Object, Object> redisTemplate;
    private DefaultRedisScript<Long>      redisScript;

    public RedisThrottler(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisScript = new DefaultRedisScript<>();
        this.redisScript.setResultType(Long.class);
        this.redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redisLimiter.lua")));
    }

    @Override
    public boolean doTryAcquire(String key, int permits) {
        // 阈值不大于0，说明不用限流
        if (window.getThreshold() <= 0) {
            return true;
        }

        Long result = redisTemplate.execute(redisScript,
                newArrayList(key),
                permits,
                // redis的expire功能，时间单位是second，所以这里要做一次转换
                window.getTimeUnit().toSeconds(window.getInterval()),
                window.getThreshold());

        return Objects.equals(result, (long) permits);
    }
}
