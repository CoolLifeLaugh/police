package com.lhsj.police.limiter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "police.limiter")
public class LimiterProperties {
    private String  enable;
    /**
     * 增强链的排序属性
     * 值越大，执行优先级越高
     */
    private Integer order;

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
