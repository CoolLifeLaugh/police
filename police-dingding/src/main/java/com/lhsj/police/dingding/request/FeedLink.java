package com.lhsj.police.dingding.request;

import java.io.Serializable;

/**
 * 声明这个类，没有复用Link，是因为，两者的属性名不一样。钉钉在设计api这块，有点随意了。
 */
public class FeedLink implements Serializable {

    private String title;
    private String messageURL;
    private String picURL;

    public FeedLink() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessageURL() {
        return messageURL;
    }

    public void setMessageURL(String messageURL) {
        this.messageURL = messageURL;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    // --------- flow api ----------

    public static FeedLink of() {
        return new FeedLink();
    }

    public FeedLink title(String title) {
        this.title = title;
        return this;
    }

    public FeedLink messageURL(String messageURL) {
        this.messageURL = messageURL;
        return this;
    }

    public FeedLink picURL(String picURL) {
        this.picURL = picURL;
        return this;
    }
}
