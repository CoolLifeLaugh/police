package com.lhsj.police.core.vo;

import java.util.List;

public class PageResponse<M, Q> {

    private Q       query;
    private List<M> data;

    public PageResponse() {
    }

    public PageResponse(Q query, List<M> data) {
        this.query = query;
        this.data = data;
    }

    public Q getQuery() {
        return query;
    }

    public void setQuery(Q query) {
        this.query = query;
    }

    public List<M> getData() {
        return data;
    }

    public void setData(List<M> data) {
        this.data = data;
    }
}