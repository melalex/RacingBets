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
    private String cacheNamespace;
    private String listCacheNamespace;
    private String countCacheNamespace;

    private Cache<String, T> cache ;
    private Cache<String, List<T>> listCache;
    private Cache<String, Long> countCache;

    CachePool(String cacheNamespace) {
        this.cacheNamespace = cacheNamespace;
        this.listCacheNamespace = getCacheListNamespace(cacheNamespace);
        this.countCacheNamespace = getCacheListNamespace(cacheNamespace);

        this.cache = Caffeine.<String, T>newBuilder().weakKeys().weakValues().build();
        this.listCache = Caffeine.<String, List<T>>newBuilder().weakKeys().weakValues().build();
        this.countCache = Caffeine.<String, Long>newBuilder().weakKeys().weakValues().build();

    }

    private String getCacheListNamespace(String name) {
        return name + ":list";
    }

    private String getCacheCountNamespace(String name) {
        return name + ":count";
    }

    public String getCacheNamespace() {
        return cacheNamespace;
    }

    public String getListCacheNamespace() {
        return listCacheNamespace;
    }

    public String getCountCacheNamespace() {
        return countCacheNamespace;
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
