package com.lhsj.police.dingding.request;

import java.io.Serializable;

public class Markdown implements Serializable {

    private String title;
    private String text;

    public Markdown() {
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

    // --------- flow api ----------

    public static Markdown of() {
        return new Markdown();
    }

    public Markdown title(String title) {
        this.title = title;
        return this;
    }

    public Markdown text(String text) {
        this.text = text;
        return this;
    }

}
