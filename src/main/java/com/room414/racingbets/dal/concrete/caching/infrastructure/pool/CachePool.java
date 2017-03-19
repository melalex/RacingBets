package com.room414.racingbets.dal.concrete.caching.infrastructure.pool;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Cache<String, T> cache;
    private Cache<String, List<T>> listCache;
    private Cache<String, Integer> countCache;

    CachePool(String cacheNamespace) {
        this.cacheNamespace = cacheNamespace;
        this.listCacheNamespace = getListCacheNamespace(cacheNamespace);
        this.countCacheNamespace = getCountCacheNamespace(cacheNamespace);

        this.cache = Caffeine.<String, T>newBuilder().weakKeys().weakValues().build();
        this.listCache = Caffeine.<String, List<T>>newBuilder().weakKeys().weakValues().build();
        this.countCache = Caffeine.<String, Long>newBuilder().weakKeys().weakValues().build();

    }

    private String getListCacheNamespace(String name) {
        return name + ":list";
    }

    private String getCountCacheNamespace(String name) {
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

    public Cache<String, Integer> getCountCache() {
        return countCache;
    }

    Map<String, Cache> getCacheByNamespaceMap() {
        Map<String, Cache> result = new HashMap<>();

        result.put(cacheNamespace, cache);
        result.put(listCacheNamespace, listCache);
        result.put(countCacheNamespace, countCache);

        return result;
    }
}
