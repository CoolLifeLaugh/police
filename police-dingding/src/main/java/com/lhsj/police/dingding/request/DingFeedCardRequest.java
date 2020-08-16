package com.lhsj.police.dingding.request;

import java.util.List;

import static com.lhsj.police.dingding.enums.MessageType.FEED_CARD;
import static java.util.Objects.isNull;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

public class DingFeedCardRequest extends AbstractDingRequest {

    private FeedCard feedCard;

    public DingFeedCardRequest() {
        super(FEED_CARD.code());
    }

    public FeedCard getFeedCard() {
        return feedCard;
    }

    public void setFeedCard(FeedCard feedCard) {
        this.feedCard = feedCard;
    }

    // --------- flow api ----------

    public static DingFeedCardRequest of() {
        return new DingFeedCardRequest();
    }

    public DingFeedCardRequest link(FeedLink link) {
        if (isNull(link)) {
            return this;
        }

        if (isNull(feedCard)) {
            feedCard = FeedCard.of().link(link);
        } else {
            feedCard.link(link);
        }
        return this;
    }

    public DingFeedCardRequest links(List<FeedLink> links) {
        if (isEmpty(links)) {
            return this;
        }

        if (isNull(feedCard)) {
            feedCard = FeedCard.of().links(links);
        } else {
            feedCard.links(links);
        }
        return this;
    }
}
