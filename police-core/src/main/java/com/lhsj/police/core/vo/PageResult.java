package com.lhsj.police.core.vo;

public class PageResult<T> {

    private Page page;
    private T    data;

    public PageResult() {
    }

    public PageResult(Page page, T data) {
        this.page = page;
        this.data = data;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}