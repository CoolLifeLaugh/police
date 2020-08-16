package com.lhsj.police.responses.configuration;

import com.lhsj.police.core.constant.Constants;
import com.lhsj.police.responses.advice.ResponsesAnnotationResponseBodyAdvice;
import com.lhsj.police.responses.interceptor.ResponsesAnnotationHandlerInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.Objects;

@Configuration
@EnableConfigurationProperties(ResponsesProperties.class)
public class ResponsesConfiguration implements BeanFactoryAware, WebMvcConfigurer {

    @Resource
    private ResponsesProperties properties;
    @Resource
    private BeanFactory         beanFactory;

    @Bean
    @ConditionalOnProperty(name = "police.responses.enable", havingValue = "true")
    @ConditionalOnMissingBean(value = {ResponsesAnnotationResponseBodyAdvice.class})
    public ResponsesAnnotationResponseBodyAdvice responsesAnnotationResponseBodyAdvice() {
        ResponsesAnnotationResponseBodyAdvice advice = new ResponsesAnnotationResponseBodyAdvice();
        advice.setBeanFactory(beanFactory);
        return advice;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (!Objects.equals(properties.getEnable(), Constants.TRUE)) {
            return;
        }

        registry.addInterceptor(new ResponsesAnnotationHandlerInterceptor());
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
