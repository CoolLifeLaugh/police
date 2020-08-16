package com.lhsj.police.dislock.redis.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static java.util.Optional.ofNullable;

@ConfigurationProperties(prefix = "police.dislock.redis")
public class DislockRedisProperties {

    private static final long DEFAULT_EXPIRE_AFTER = 60000;

    private String enable;
    /**
     * 增强链的排序属性
     * 值越大，执行优先级越高
     */
    private Integer order;
    /**
     * 存储在redis的数据的失效时间
     * <p>
     * 单位：毫秒
     */
    private Long   expireAfter;

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

    public Long getExpireAfter() {
        return ofNullable(expireAfter).filter(e -> e > 0).orElse(DEFAULT_EXPIRE_AFTER);
    }

    public void setExpireAfter(Long expireAfter) {
        this.expireAfter = expireAfter;
    }
}
