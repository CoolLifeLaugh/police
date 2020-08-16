package com.lhsj.police.trace.configuration;

import com.lhsj.police.trace.aop.TraceAnnotationAdvisor;
import com.lhsj.police.trace.aop.TraceAnnotationInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

import static java.util.Optional.ofNullable;

@Configuration
@EnableConfigurationProperties(TraceProperties.class)
public class TraceConfiguration {

    @Resource
    private TraceProperties properties;

    @Bean
    @ConditionalOnProperty(name = "police.trace.enable", havingValue = "true")
    public TraceAnnotationAdvisor startTraceAnnotationAdvisor() {
        TraceAnnotationAdvisor advisor = new TraceAnnotationAdvisor(new TraceAnnotationInterceptor());
        ofNullable(properties).map(TraceProperties::getOrder).ifPresent(advisor::setOrder);
        return advisor;
    }

}
