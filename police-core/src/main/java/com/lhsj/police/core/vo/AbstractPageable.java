package com.lhsj.police.core.vo;

import static java.util.Optional.ofNullable;

public abstract class AbstractPageable {

    /**
     * 当前页码, 默认从1开始
     */
    private Integer currPage;
    /**
     * 每页条数
     */
    private Integer pageSize;
    /**
     * 总条数
     */
    private Integer itemCount;
    /**
     * 总页数
     */
    private Integer pageCount;
    /**
     * 记录开始位置
     */
    private Integer itemStart;

    public Page page() {
        return new Page(currPage, pageSize);
    }

    // flow api

    public AbstractPageable itemStart(int itemStart) {
        this.itemStart = itemStart;
        return this;
    }

    public AbstractPageable pageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public int getCurrPage() {
        return ofNullable(currPage).filter(e -> e > 0).orElse(1);
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return ofNullable(pageSize).filter(e -> e > 3).orElse(3);
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getItemStart() {
        return itemStart;
    }

    public void setItemStart(int itemStart) {
        this.itemStart = itemStart;
    }
}