package com.lhsj.police.dingding.request;

import java.io.Serializable;

public class Link implements Serializable {

    private String title;
    private String text;
    private String picUrl;
    private String messageUrl;

    public Link() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMessageUrl() {
        return messageUrl;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }

    // --------- flow api ----------

    public static Link of() {
        return new Link();
    }

    public Link title(String title) {
        this.title = title;
        return this;
    }

    public Link text(String text) {
        this.text = text;
        return this;
    }

    public Link picUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    public Link messageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
        return this;
    }
}
