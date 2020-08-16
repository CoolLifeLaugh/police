package com.lhsj.police.dingding.configuration;

import com.lhsj.police.dingding.aop.DingDingAnnotationAdvisor;
import com.lhsj.police.dingding.aop.DingDingAnnotationInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

import static java.util.Optional.ofNullable;

@Configuration
@EnableConfigurationProperties(DingDingProperties.class)
public class DingDingConfiguration {

    @Resource
    private DingDingProperties properties;

    @Bean
    @ConditionalOnProperty(name = "police.dingding.enable", havingValue = "true")
    public DingDingAnnotationAdvisor dingDingAnnotationAdvisor() {
        DingDingAnnotationAdvisor advisor = new DingDingAnnotationAdvisor(new DingDingAnnotationInterceptor());
        ofNullable(properties).map(DingDingProperties::getOrder).ifPresent(advisor::setOrder);
        return advisor;
    }

}
