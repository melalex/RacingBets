package com.room414.racingbets.dal.concrete.caching.caffeine.caches;

import com.fasterxml.jackson.core.type.TypeReference;
import com.room414.racingbets.dal.abstraction.cache.RaceCache;
import com.room414.racingbets.dal.abstraction.cache.RacecourseCache;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.BaseCache;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.CachePool;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;
import com.room414.racingbets.dal.domain.entities.Racecourse;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class CaffeineRacecourseCache extends BaseCache<Racecourse> implements RacecourseCache {
    private static final TypeReference<Racecourse> TYPE = new TypeReference<Racecourse>() {};
    private static final TypeReference<List<Racecourse>> LIST_TYPE = new TypeReference<List<Racecourse>>() {};

    private RaceCache raceCache;

    public CaffeineRacecourseCache(
            CachePool<Racecourse> cachePool,
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
