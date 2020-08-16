package com.lhsj.police.dingding.request;

import java.io.Serializable;

public class Text implements Serializable {

    private String content;

    public Text() {
    }

    public Text(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // --------- flow api ----------

    public static Text of() {
        return new Text();
    }

    public Text content(String content) {
        this.content = content;
        return this;
    }
}
