package com.room414.racingbets.dal.abstraction.caching;

import java.util.concurrent.Callable;

/**
 * @author melalex
 * @version 1.0 12 Mar 2017
 */
public interface DaoCache {
    <T> T get(String key, Callable<T> getter, Class<T> type) throws Exception;
    void delete(String key);
}
