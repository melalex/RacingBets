package com.room414.racingbets.dal.abstraction.cache;


import com.room414.racingbets.dal.abstraction.infrastructure.Getter;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public interface EntityCache<T> {
    T getOneCached(String key, Getter<T> getter);
    List<T> getManyCached(String key, Getter<List<T>> getter);
    long getCachedCount(String key, Getter<Long> getter);

    void deleteOneCached(String key);
    void deleteAllCached();
    void deleteManyCached();
}
