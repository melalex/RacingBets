package com.room414.racingbets.dal.concrete.caching.infrastructure.pool;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 15 Mar 2017
 */
// TODO: Reference-based Eviction
// TODO: Memory lick
public class CachePool<T> {
    private Cache<String, T> cache ;
    private Cache<String, List<T>> listCache;
    private Cache<String, Long> countCache;

    CachePool() {
        this.cache = Caffeine.<String, T>newBuilder().weakKeys().weakValues().build();
        this.listCache = Caffeine.<String, List<T>>newBuilder().weakKeys().weakValues().build();
        this.countCache = Caffeine.<String, Long>newBuilder().weakKeys().weakValues().build();
    }

    public Cache<String, T> getCache() {
        return cache;
    }

    public Cache<String, List<T>> getListCache() {
        return listCache;
    }

    public Cache<String, Long> getCountCache() {
        return countCache;
    }
}
