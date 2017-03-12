package com.room414.racingbets.dal.abstraction.caching;

import java.util.concurrent.Callable;

/**
 * @author melalex
 * @version 1.0 12 Mar 2017
 */
public interface Cache<T> {
    T get(String key, Callable<T> getter);
    T update(String key, T value);
    T delete(String key);

    void commit();
    void rollback();
}
