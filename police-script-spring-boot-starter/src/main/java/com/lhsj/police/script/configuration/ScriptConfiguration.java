package com.lhsj.police.script.configuration;

import com.lhsj.police.script.controller.ScriptController;
import com.lhsj.police.script.listener.ScriptControllerListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ScriptProperties.class)
public class ScriptConfiguration {

    @Bean
    @ConditionalOnProperty(name = "police.script.enable", havingValue = "true")
    public ScriptControllerListener policeScriptControllerListener() {
        return new ScriptControllerListener();
    }

    @Bean
    @ConditionalOnProperty(name = "police.script.enable", havingValue = "true")
    public ScriptController policeScriptController() {
        return new ScriptController();
    }

}
