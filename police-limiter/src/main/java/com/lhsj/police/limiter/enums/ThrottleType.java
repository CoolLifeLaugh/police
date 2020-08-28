package com.lhsj.police.limiter.enums;

import com.lhsj.police.core.enums.EnumAware;
import org.apache.commons.lang3.StringUtils;

import static java.util.Optional.ofNullable;

@SuppressWarnings("unused")
public enum ThrottleType implements EnumAware<String> {

    SIMPLE_WINDOW("简单窗口"),
    SLIDING_WINDOW("滑动窗口"),
    LEAKY_BUCKET("漏桶"),
    TOKEN_BUCKET("令牌桶"),
    SLIDING_LOG("滑动日志"),
    REDIS_SIMPLE_WINDOW("分布式REDIS简单窗口"),
    ;

    private String desc;

    ThrottleType(String desc) {
        this.desc = desc;
    }

    @Override
    public String code() {
        return this.name().toUpperCase();
    }

    @Override
    public String desc() {
        return this.desc;
    }

    public static ThrottleType from(String code) {
        return ofNullable(code)
                .filter(StringUtils::isNotBlank)
                .map(String::toUpperCase)
                .map(ThrottleType::valueOf)
                .orElse(null);
    }

    public boolean match(String code) {
        return this == from(code);
    }
}
