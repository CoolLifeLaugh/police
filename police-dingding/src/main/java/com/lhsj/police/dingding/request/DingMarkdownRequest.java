package com.lhsj.police.dingding.request;

import java.util.List;

import static com.lhsj.police.dingding.enums.MessageType.MARKDOWN;
import static java.util.Objects.nonNull;

public class DingMarkdownRequest extends AbstractDingRequest {

    private Markdown markdown;
    private At       at;

    public DingMarkdownRequest() {
        super(MARKDOWN.code());
    }

    public Markdown getMarkdown() {
        return markdown;
    }

    public void setMarkdown(Markdown markdown) {
        this.markdown = markdown;
    }

    public At getAt() {
        return at;
    }

    public void setAt(At at) {
        this.at = at;
    }

    // --------- flow api ----------

    public static DingMarkdownRequest of() {
        return new DingMarkdownRequest();
    }

    public DingMarkdownRequest title(String title) {
        if (nonNull(markdown)) {
            markdown.title(title);
        } else {
            markdown = Markdown.of().title(title);
        }
        return this;
    }

    public DingMarkdownRequest text(String text) {
        if (nonNull(markdown)) {
            markdown.text(text);
        } else {
            markdown = Markdown.of().text(text);
        }
        return this;
    }

    public DingMarkdownRequest atMobiles(List<String> atMobiles) {
        if (nonNull(at)) {
            at.atMobiles(atMobiles);
        } else {
            at = At.of().atMobiles(atMobiles);
        }
        return this;
    }

    public DingMarkdownRequest atAll(boolean atAll) {
        if (nonNull(at)) {
            at.atAll(atAll);
        } else {
            at = At.of().atAll(atAll);
        }
        return this;
    }
}
