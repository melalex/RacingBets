package com.room414.racingbets.dal.concrete.caching.caffeine.caches;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.benmanes.caffeine.cache.Cache;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.BaseCache;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;
import com.room414.racingbets.dal.domain.entities.Racecourse;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class RacecourseCache extends BaseCache<Racecourse> {
    private static final String NAME_SPACE = "racecourse";
    private static final String LIST_NAME_SPACE = "racecourse:list";
    private static final String COUNT_NAME_SPACE = "racecourse:count";

    private static final TypeReference<Racecourse> TYPE = new TypeReference<Racecourse>() {};
    private static final TypeReference<List<Racecourse>> LIST_TYPE = new TypeReference<List<Racecourse>>() {};

    private RaceCache raceCache;

    public RacecourseCache(
            Cache<String, Racecourse> cache,
            Cache<String, List<Racecourse>> cacheList,
            Cache<String, Long> countCache,
            RedisCache redisCache,
            RaceCache raceCache
    ) {
        super(NAME_SPACE, LIST_NAME_SPACE, COUNT_NAME_SPACE, TYPE, LIST_TYPE, cache, cacheList, countCache, redisCache);
        this.raceCache = raceCache;
    }

    @Override
    public void deleteOneCached(String key) {
        super.deleteOneCached(key);
        raceCache.deleteAllCached();
    }
}
