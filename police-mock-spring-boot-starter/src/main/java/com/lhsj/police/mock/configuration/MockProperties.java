package com.lhsj.police.mock.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.lhsj.police.core.constant.Constants.EQUAL;

@ConfigurationProperties(prefix = "police.mock")
public class MockProperties {
    private String  enable;
    private String  expression;
    /**
     * 分隔符：分隔符左边是key，右边是mock的数据
     */
    private String  separator = EQUAL;
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

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
