package com.room414.racingbets.web.infrastructure;

import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;

import java.io.Serializable;

/**
 * @author Alexander Melashchenko
 * @version 1.0 24 Mar 2017
 */
public class PagerImpl implements Pager, Serializable {
    private static final long serialVersionUID = -8178968734227331056L;

    private int limit;
    private int page;
    private int count;

    public PagerImpl(int limit, int page) {
        this.limit = limit;
        this.page = page;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public int getOffset() {
        return limit * (page - 1);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PagerImpl pager = (PagerImpl) o;

        if (limit != pager.limit) return false;
        if (page != pager.page) return false;
        return count == pager.count;
    }

    @Override
    public int hashCode() {
        int result = limit;
        result = 31 * result + page;
        result = 31 * result + count;
        return result;
    }

    @Override
    public String toString() {
        return "PagerImpl{" +
                "limit=" + limit +
                ", page=" + page +
                ", count=" + count +
                ", offset=" + getOffset() +
                '}';
    }
}
