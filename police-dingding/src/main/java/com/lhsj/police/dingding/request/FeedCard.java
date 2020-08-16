package com.lhsj.police.dingding.request;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

import static java.util.Objects.isNull;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

public class FeedCard implements Serializable {

    private List<FeedLink> links;

    public FeedCard() {
    }

    public List<FeedLink> getLinks() {
        return links;
    }

    public void setLinks(List<FeedLink> links) {
        this.links = links;
    }

    // --------- flow api ----------

    public static FeedCard of() {
        return new FeedCard();
    }

    public FeedCard link(FeedLink link) {
        if (isNull(link)) {
            return this;
        }

        if (isNull(links)) {
            links = Lists.newArrayList(link);
        } else {
            links.add(link);
        }
        return this;
    }

    public FeedCard links(List<FeedLink> links) {
        if (isEmpty(links)) {
            return this;
        }

        if (isNull(this.links)) {
            this.links = Lists.newArrayList(links);
        } else {
            this.links.addAll(links);
        }
        return this;
    }
}
