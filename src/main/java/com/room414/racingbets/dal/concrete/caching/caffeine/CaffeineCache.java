package com.room414.racingbets.dal.concrete.caching.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.room414.racingbets.dal.abstraction.caching.DaoCache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class CaffeineCache implements DaoCache {
    private DaoCache lowLevelCache;
    private Cache<String, Object> cache;
    private Map<String, Object> toUpdate = new HashMap<>();
    private List<String> toDelete = new  LinkedList<>();

    public CaffeineCache(Cache<String, Object> cache, DaoCache lowLevelCache) {
        this.cache = cache;
        this.lowLevelCache = lowLevelCache;
    }


    @Override
    public <T> T get(String key, Callable<T> getter) {
        return ((T) cache.get(key, k -> lowLevelCache.get(k, getter)));
    }


    @Override
    public void delete(String key) {

    }
}
