package com.lhsj.police.context.configuration;

import com.lhsj.police.context.holder.ApplicationContextHolder;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@Configuration
@EnableConfigurationProperties(ContextProperties.class)
public class ContextConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Bean
    @ConditionalOnProperty(name = "police.application.context.enable", havingValue = "true")
    public ApplicationContextHolder policeApplicationContextHolder() {
        ApplicationContextHolder holder = new ApplicationContextHolder();
        holder.setContext(applicationContext);
        return holder;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
