package com.room414.racingbets.dal.concrete.caching.caffeine.caches;

import com.fasterxml.jackson.core.type.TypeReference;
import com.room414.racingbets.dal.abstraction.cache.JockeyCache;
import com.room414.racingbets.dal.abstraction.cache.RaceCache;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.BaseCache;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.CachePool;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;
import com.room414.racingbets.dal.domain.entities.Jockey;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class CaffeineJockeyCache extends BaseCache<Jockey> implements JockeyCache {
    private static final String NAME_SPACE = "jockey";
    private static final String LIST_NAME_SPACE = "jockey:list";
    private static final String COUNT_NAME_SPACE = "jockey:count";

    private static final TypeReference<Jockey> TYPE = new TypeReference<Jockey>() {};
    private static final TypeReference<List<Jockey>> LIST_TYPE = new TypeReference<List<Jockey>>() {};

    private RaceCache raceCache;

    public CaffeineJockeyCache(
            CachePool<Jockey> cachePool,
            RedisCache redisCache,
            RaceCache raceCache
    ) {
        super(cachePool, TYPE, LIST_TYPE, redisCache);
        this.raceCache = raceCache;
    }

    @Override
    public void deleteOneCached(String key) {
        super.deleteOneCached(key);
        raceCache.deleteAllCached();
    }
}
