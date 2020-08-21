package com.lhsj.police.dislock.redis.service;

import com.lhsj.police.dislock.annotation.Dislock;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static java.util.Optional.ofNullable;

@Service
public class DislockRedisService implements InitializingBean {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Dislock(key = "#root.methodName + '_key_' + #key")
    public boolean simple(String key) {
        if (!ofNullable(stringRedisTemplate.hasKey(key)).orElse(false)) {
            stringRedisTemplate.opsForValue().set(key, String.valueOf(0));
        }

        stringRedisTemplate.boundValueOps(key).increment(1);
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory);
    }
}
