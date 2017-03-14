package com.room414.racingbets.dal.concrete.caching.caffeine.caches;

import com.github.benmanes.caffeine.cache.Cache;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.infrastructure.Getter;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class CacheHelper {
    private CacheHelper() {

    }

    // TODO: is good?
    public static  <K, V> V getCached(Cache<K, V> cache, K key, Getter<V> getter) throws DalException {
        try {
            return cache.get(key, k -> {
                try {
                    return getter.call();
                } catch (DalException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (RuntimeException e) {
            throw new DalException(e);
        }
    }
}
