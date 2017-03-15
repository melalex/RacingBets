package com.room414.racingbets.dal.concrete.caching.infrastructure.pool;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 15 Mar 2017
 */
// TODO: Reference-based Eviction
public class CachePool<T> {
    private Cache<String, T> cache;
    private Cache<String, List<T>> listCache;
    private Cache<String, Long> countCache;

    public Cache<String, T> getCache() {
        if (cache == null) {
            this.cache = Caffeine.<String, T>newBuilder().weakKeys().weakValues().build();
        }
        return cache;
    }

    public Cache<String, List<T>> getListCache() {
        if (listCache == null) {
            this.listCache = Caffeine.<String, List<T>>newBuilder().weakKeys().weakValues().build();
        }
        return listCache;
    }

    public Cache<String, Long> getCountCache() {
        if (countCache == null) {
            this.countCache = Caffeine.<String, Long>newBuilder().weakKeys().weakValues().build();
        }
        return countCache;
    }
}
