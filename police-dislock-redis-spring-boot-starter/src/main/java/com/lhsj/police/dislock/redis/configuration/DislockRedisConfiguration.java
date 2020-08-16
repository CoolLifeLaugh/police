package com.lhsj.police.dislock.redis.configuration;

import com.lhsj.police.dislock.redis.aop.DislockRedisAnnotationAdvisor;
import com.lhsj.police.dislock.redis.aop.DislockRedisAnnotationInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

import javax.annotation.Resource;

import static java.util.Optional.ofNullable;

@Configuration
@EnableConfigurationProperties(DislockRedisProperties.class)
public class DislockRedisConfiguration {

    @Resource
    private DislockRedisProperties properties;

    @Bean
    @ConditionalOnProperty(name = "police.dislock.redis.enable", havingValue = "true")
    public DislockRedisAnnotationAdvisor dingDingAnnotationAdvisor() {
        DislockRedisAnnotationAdvisor advisor = new DislockRedisAnnotationAdvisor(new DislockRedisAnnotationInterceptor());
        ofNullable(properties).map(DislockRedisProperties::getOrder).ifPresent(advisor::setOrder);
        return advisor;
    }

    @Bean
    @ConditionalOnProperty(name = "police.dislock.redis.enable", havingValue = "true")
    @ConditionalOnMissingBean(RedisConnectionFactory.class)
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        return new RedisLockRegistry(
                redisConnectionFactory
                , "police-dislock"
                , properties.getExpireAfter()
        );
    }

}
