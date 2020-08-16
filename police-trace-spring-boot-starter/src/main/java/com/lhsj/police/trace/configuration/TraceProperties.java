package com.lhsj.police.trace.configuration;

import com.lhsj.police.trace.global.TraceGlobal;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "police.trace")
public class TraceProperties {
    private String  app;
    private String  enable;
    /**
     * 增强链的排序属性
     * 值越大，执行优先级越高
     */
    private Integer order;


    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
        TraceGlobal.app = app;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
