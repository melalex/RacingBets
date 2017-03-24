package com.room414.racingbets.bll.abstraction.infrastructure.pagination;

/**
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public interface Pager {
    int getPage();
    void setPage(int page);
    int getLimit();
    int getOffset();
    int getCount();
    void setCount(int count);
}
