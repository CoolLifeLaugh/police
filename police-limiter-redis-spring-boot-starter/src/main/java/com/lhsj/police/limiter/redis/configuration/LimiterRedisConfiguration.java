package com.lhsj.police.limiter.redis.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhsj.police.limiter.redis.aop.LimiterRedisAnnotationAdvisor;
import com.lhsj.police.limiter.redis.aop.LimiterRedisAnnotationInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

import static java.util.Optional.ofNullable;

@Configuration
@EnableConfigurationProperties(LimiterRedisProperties.class)
public class LimiterRedisConfiguration {

    @Resource
    private LimiterRedisProperties properties;

    @Bean
    @ConditionalOnProperty(name = "police.limiter.redis.enable", havingValue = "true")
    public LimiterRedisAnnotationAdvisor limiterRedisAnnotationAdvisor(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = createRedisTemplate(redisConnectionFactory);

        LimiterRedisAnnotationInterceptor interceptor = new LimiterRedisAnnotationInterceptor(redisTemplate);
        LimiterRedisAnnotationAdvisor advisor = new LimiterRedisAnnotationAdvisor(interceptor);
        ofNullable(properties).map(LimiterRedisProperties::getOrder).ifPresent(advisor::setOrder);
        return advisor;
    }

    private RedisTemplate<Object, Object> createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        // 使用Jackson2JsonRedisSerializer，序列化和反序列化value值
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        serializer.setObjectMapper(mapper);
        template.setValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }

}
