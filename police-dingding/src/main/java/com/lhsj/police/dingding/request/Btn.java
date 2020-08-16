package com.lhsj.police.dingding.request;

import java.io.Serializable;

public class Btn implements Serializable {

    private String title;
    private String actionURL;

    public Btn() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActionURL() {
        return actionURL;
    }

    public void setActionURL(String actionURL) {
        this.actionURL = actionURL;
    }

    // --------- flow api ----------

    public static Btn of() {
        return new Btn();
    }

    public Btn title(String title) {
        this.title = title;
        return this;
    }

    public Btn actionURL(String actionURL) {
        this.actionURL = actionURL;
        return this;
    }
}
