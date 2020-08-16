package com.lhsj.police.core.vo;

import java.io.Serializable;

/**
 * 设计原则：利用最少的变量，实现够用的分页功能
 * <p>
 * 兼容MySQL分页查询语句
 * SQL1：SELECT * FROM order WHERE order_type = 1 LIMIT 10000, 10
 * SQL2：SELECT * FROM order WHERE id >=
 * (SELECT id FROM order WHERE order_type = 1 LIMIT 10000, 1) and order_type = 1 LIMIT 10
 * 第一种SQL，不适合数据量比较大的翻页；
 * 第二种SQL，性能比较好
 * <p>
 * 使用TDDL，直接使用第一种写法，因为框架会将SQL优化成第二种；
 * 如果使用MySQL，数据量大，经常有大于100页以上的场景，建议第二种写法。
 */
public class Page implements Serializable {

    private static final int DEFAULT_SIZE = 20;

    /**
     * 当前页码, 默认从1开始
     */
    private int currPage;
    /**
     * 每页条数
     */
    private int pageSize;
    /**
     * 总条数
     */
    private int itemCount;
    /**
     * 总页数
     */
    private int pageCount;
    /**
     * 记录开始位置
     */
    private int itemStart;

    public Page() {
        this.currPage = 1;
        this.pageSize = DEFAULT_SIZE;
    }

    public Page(int currPage, int pageSize) {
        this.currPage = currPage;
        this.pageSize = pageSize;
    }

    public Page(int currPage, int pageSize, int itemCount, int pageCount) {
        this.currPage = currPage;
        this.pageSize = pageSize;
        this.itemCount = itemCount;
        this.pageCount = pageCount;
    }

    public Page calc() {
        // calc size
        if (pageSize <= 0) {
            pageSize = DEFAULT_SIZE;
        }
        // calc currPage
        if (currPage <= 0) {
            currPage = 1;
        }
        // calc itemStart
        itemStart = (currPage - 1) * pageSize;
        // calc itemCount
        if (itemCount < 0) {
            itemCount = 0;
            pageCount = 0;
            return this;
        }
        // calc pageCount
        if (itemCount % pageSize == 0) {
            pageCount = itemCount / pageSize;
        } else {
            pageCount = (itemCount / pageSize) + 1;
        }
        // re-calc currPage and itemStart
        if (currPage > pageCount) {
            currPage = 1;
            itemStart = 0;
        }
        return this;
    }

    // useful api

    public int nextPage() {
        calc();
        return currPage < pageCount ? currPage + 1 : pageCount;
    }

    public int lastPage() {
        calc();
        return currPage == 0 ? currPage : currPage - 1;
    }

    public Page clone(Page old) {
        return new Page(old.getCurrPage(), old.getPageSize(), old.getItemCount(), old.getPageCount()).calc();
    }

    // flow api

    public Page currPage(int currPage) {
        this.currPage = currPage;
        return this;
    }

    public Page pageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Page itemCount(int itemCount) {
        this.itemCount = itemCount;
        return this;
    }

    public Page pageCount(int pageCount) {
        this.pageCount = pageCount;
        return this;
    }

    // setter and getter

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
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

    @Override
    public String toString() {
        return "Page{" + "currPage=" + currPage + ", pageSize=" + pageSize + ", itemCount=" + itemCount + ", " +
                "pageCount=" + pageCount + ", itemStart=" + itemStart + '}';
    }
}
