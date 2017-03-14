package com.room414.racingbets.dal.concrete.caching.caffeine;


import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.infrastructure.Getter;

import java.io.IOException;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public interface CaffeineCache<T> {
    T getOneCached(String key, Getter<T> getter) throws DalException, IOException;
    List<T> getManyCached(String key, Getter<List<T>> getter) throws DalException;
    long getCachedCount(String key, Getter<Long> getter) throws DalException;

    void deleteOneCached(String key);
    void deleteAllCached();
    void deleteManyCached();
}
