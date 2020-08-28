package com.lhsj.police.limiter.configuration;

import com.lhsj.police.limiter.aop.LimiterAnnotationAdvisor;
import com.lhsj.police.limiter.aop.LimiterAnnotationInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

import static java.util.Optional.ofNullable;

@Configuration
@EnableConfigurationProperties(LimiterProperties.class)
public class LimiterConfiguration {

    @Resource
    private LimiterProperties properties;

    @Bean
    @ConditionalOnProperty(name = "police.limiter.enable", havingValue = "true")
    public LimiterAnnotationAdvisor limiterAnnotationAdvisor() {
        LimiterAnnotationAdvisor advisor = new LimiterAnnotationAdvisor(new LimiterAnnotationInterceptor());
        ofNullable(properties).map(LimiterProperties::getOrder).ifPresent(advisor::setOrder);
        return advisor;
    }

}
