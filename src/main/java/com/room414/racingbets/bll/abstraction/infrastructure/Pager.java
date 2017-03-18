package com.room414.racingbets.bll.abstraction.infrastructure;

/**
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public interface Pager {
    int getLimit();
    int getOffset();
    void setCount(int count);
}
