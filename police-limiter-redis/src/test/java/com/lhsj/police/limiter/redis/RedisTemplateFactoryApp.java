package com.lhsj.police.limiter.redis;

import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

public class RedisTemplateFactoryApp {

    public static void main(String[] args) {
        RedisProperties properties = new RedisProperties();
        properties.setHost("127.0.0.1");
        properties.setPort(6379);
        properties.setClientName("redis-client");
        properties.setTimeout(Duration.ofSeconds(3));

        RedisTemplate<String, Object> redisTemplate = RedisTemplateFactory.of()
                .properties(properties)
                .clientResources(DefaultClientResources.create())
                .create();

        Object result = redisTemplate.opsForValue().get("rank");
        System.out.println(result);
    }

}
