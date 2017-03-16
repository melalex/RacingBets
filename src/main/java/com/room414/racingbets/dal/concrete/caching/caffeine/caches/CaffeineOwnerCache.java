package com.room414.racingbets.dal.concrete.caching.caffeine.caches;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.benmanes.caffeine.cache.Cache;
import com.room414.racingbets.dal.abstraction.cache.HorseCache;
import com.room414.racingbets.dal.abstraction.cache.OwnerCache;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.BaseCache;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;
import com.room414.racingbets.dal.domain.entities.Owner;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class CaffeineOwnerCache extends BaseCache<Owner> implements OwnerCache {
    private static final String NAME_SPACE = "owner";
    private static final String LIST_NAME_SPACE = "owner:list";
    private static final String COUNT_NAME_SPACE = "owner:count";

    private static final TypeReference<Owner> TYPE = new TypeReference<Owner>() {};
    private static final TypeReference<List<Owner>> LIST_TYPE = new TypeReference<List<Owner>>() {};

    private HorseCache horseCache;

    public CaffeineOwnerCache(
            Cache<String, Owner> cache,
            Cache<String, List<Owner>> cacheList,
            Cache<String, Long> countCache,
            RedisCache redisCache,
            HorseCache horseCache
    ) {
        super(NAME_SPACE, LIST_NAME_SPACE, COUNT_NAME_SPACE, TYPE, LIST_TYPE, cache, cacheList, countCache, redisCache);
        this.horseCache = horseCache;
    }

    @Override
    public void deleteOneCached(String key) {
        super.deleteOneCached(key);
        horseCache.deleteAllCached();
    }
}
