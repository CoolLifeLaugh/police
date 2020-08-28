package com.lhsj.police.mock.configuration;

import com.lhsj.police.mock.aop.MockAnnotationAdvisor;
import com.lhsj.police.mock.aop.MockAnnotationInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

import static java.util.Optional.ofNullable;

@Configuration
@EnableConfigurationProperties(MockProperties.class)
public class MockConfiguration {

    @Resource
    private MockProperties properties;

    @Bean
    @ConditionalOnProperty(name = "police.mock.enable", havingValue = "true")
    public MockAnnotationAdvisor mockAnnotationAdvisor() {
        MockAnnotationAdvisor advisor = new MockAnnotationAdvisor(new MockAnnotationInterceptor(properties));
        ofNullable(properties).map(MockProperties::getOrder).ifPresent(advisor::setOrder);
        return advisor;
    }

}
