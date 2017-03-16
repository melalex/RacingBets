package com.room414.racingbets.dal.concrete.caching.caffeine.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.benmanes.caffeine.cache.Cache;
import com.room414.racingbets.dal.abstraction.infrastructure.Getter;
import com.room414.racingbets.dal.abstraction.cache.EntityCache;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;

import java.util.List;


/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public abstract class BaseCache<T> implements EntityCache<T> {
    private String nameSpace;
    private String listNameSpace;
    private String countNameSpace;

    private TypeReference<T> type;
    private TypeReference<List<T>> listType;

    private Cache<String, T> cache;
    private Cache<String, List<T>> cacheList;
    private Cache<String, Long> countCache;
    protected RedisCache redisCache;

    public BaseCache(
            String nameSpace,
            String listNameSpace,
            String countNameSpace,
            TypeReference<T> type,
            TypeReference<List<T>> listType,
            Cache<String, T> cache,
            Cache<String, List<T>> cacheList,
            Cache<String, Long> countCache,
            RedisCache redisCache
    ) {
        this.nameSpace = nameSpace;
        this.listNameSpace = listNameSpace;
        this.countNameSpace = countNameSpace;
        this.type = type;
        this.listType = listType;
        this.cache = cache;
        this.cacheList = cacheList;
        this.countCache = countCache;
        this.redisCache = redisCache;
    }

    @Override
    public T getOneCached(String key, Getter<T> getter) {
        return cache.get(
                key,
                k -> redisCache.getCached(nameSpace, key, getter, type)
        );
    }

    @Override
    public List<T> getManyCached(String key, Getter<List<T>> getter) {
        return cacheList.get(
                key,
                k -> redisCache.getCached(listNameSpace, key, getter, listType)
        );
    }

    @Override
    public long getCachedCount(String key, Getter<Long> getter) {
        return countCache.get(
                key,
                k -> redisCache.getCachedCount(listNameSpace, key, getter)
        );
    }

    @Override
    public void deleteOneCached(String key) {
        cache.invalidate(key);
        redisCache.delete(nameSpace, key);
    }

    @Override
    public void deleteAllCached() {
        cache.invalidateAll();
        cacheList.invalidateAll();
        countCache.invalidateAll();
        redisCache.delete(nameSpace);
        redisCache.delete(listNameSpace);
        redisCache.delete(countNameSpace);
    }

    @Override
    public void deleteManyCached() {
        cacheList.invalidateAll();
        countCache.invalidateAll();
        redisCache.delete(listNameSpace);
        redisCache.delete(countNameSpace);
    }
}