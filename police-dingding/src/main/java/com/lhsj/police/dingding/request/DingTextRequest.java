package com.lhsj.police.dingding.request;

import java.util.List;

import static com.lhsj.police.dingding.enums.MessageType.TEXT;
import static java.util.Objects.nonNull;

public class DingTextRequest extends AbstractDingRequest {

    private Text text;
    private At   at;

    public DingTextRequest() {
        super(TEXT.code());
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public At getAt() {
        return at;
    }

    public void setAt(At at) {
        this.at = at;
    }

    // --------- flow api ----------

    public static DingTextRequest of() {
        return new DingTextRequest();
    }

    public DingTextRequest content(String content) {
        if (nonNull(text)) {
            text.content(content);
        } else {
            text = Text.of().content(content);
        }
        return this;
    }

    public DingTextRequest atMobiles(List<String> atMobiles) {
        if (nonNull(at)) {
            at.atMobiles(atMobiles);
        } else {
            at = At.of().atMobiles(atMobiles);
        }
        return this;
    }

    public DingTextRequest atAll(boolean atAll) {
        if (nonNull(at)) {
            at.atAll(atAll);
        } else {
            at = At.of().atAll(atAll);
        }
        return this;
    }
}
