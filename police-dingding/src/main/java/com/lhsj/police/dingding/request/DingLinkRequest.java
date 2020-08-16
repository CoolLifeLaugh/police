package com.lhsj.police.dingding.request;

import static com.lhsj.police.dingding.enums.MessageType.LINK;
import static java.util.Objects.nonNull;

public class DingLinkRequest extends AbstractDingRequest {

    private Link link;

    public DingLinkRequest() {
        super(LINK.code());
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    // --------- flow api ----------

    public static DingLinkRequest of() {
        return new DingLinkRequest();
    }

    public DingLinkRequest title(String title) {
        if (nonNull(link)) {
            link.title(title);
        } else {
            link = Link.of().title(title);
        }
        return this;
    }

    public DingLinkRequest text(String text) {
        if (nonNull(link)) {
            link.text(text);
        } else {
            link = Link.of().text(text);
        }
        return this;
    }

    public DingLinkRequest picUrl(String picUrl) {
        if (nonNull(link)) {
            link.picUrl(picUrl);
        } else {
            link = Link.of().picUrl(picUrl);
        }
        return this;
    }

    public DingLinkRequest messageUrl(String messageUrl) {
        if (nonNull(link)) {
            link.messageUrl(messageUrl);
        } else {
            link = Link.of().messageUrl(messageUrl);
        }
        return this;
    }
}
