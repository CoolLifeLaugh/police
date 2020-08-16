package com.lhsj.police.exceptions.configuration;

import com.lhsj.police.exceptions.aop.ExceptionResolverAnnotationAdvisor;
import com.lhsj.police.exceptions.aop.ExceptionResolverAnnotationInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

import static java.util.Optional.ofNullable;

@Configuration
@EnableConfigurationProperties(ExceptionsProperties.class)
public class ExceptionsConfiguration {

    @Resource
    private ExceptionsProperties properties;

    @Bean
    @ConditionalOnProperty(name = "police.exceptions.enable", havingValue = "true")
    public ExceptionResolverAnnotationAdvisor dingDingAnnotationAdvisor() {
        ExceptionResolverAnnotationAdvisor advisor = new ExceptionResolverAnnotationAdvisor(new ExceptionResolverAnnotationInterceptor());
        ofNullable(properties).map(ExceptionsProperties::getOrder).ifPresent(advisor::setOrder);
        return advisor;
    }

}
