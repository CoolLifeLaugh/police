package com.lhsj.police.dingding.enums;

import com.lhsj.police.core.enums.EnumAware;

public enum MessageType implements EnumAware<String> {
    TEXT("text", "文本类型"),
    LINK("link", "链接类型"),
    MARKDOWN("markdown", "MARKDOWN类型"),
    SINGLE_ACTION_CARD("actionCard", "整体跳转ActionCard类型"),
    MULTI_ACTION_CARD("actionCard", "独立跳转ActionCard类型"),
    FEED_CARD("feedCard", "FeedCard类型"),
    ;

    private String code;
    private String desc;

    MessageType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String desc() {
        return desc;
    }
}
