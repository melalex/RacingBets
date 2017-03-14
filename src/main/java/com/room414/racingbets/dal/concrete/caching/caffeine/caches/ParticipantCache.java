package com.room414.racingbets.dal.concrete.caching.caffeine.caches;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.benmanes.caffeine.cache.Cache;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.BaseCache;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;
import com.room414.racingbets.dal.domain.entities.Participant;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class ParticipantCache extends BaseCache<Participant> {
    private static final String NAME_SPACE = "participant";
    private static final String LIST_NAME_SPACE = "participant:list";
    private static final String COUNT_NAME_SPACE = "participant:count";

    private static final TypeReference<Participant> TYPE = new TypeReference<Participant>() {};
    private static final TypeReference<List<Participant>> LIST_TYPE = new TypeReference<List<Participant>>() {};

    private RaceCache raceCache;

    public ParticipantCache(
            Cache<String, Participant> cache,
            Cache<String, List<Participant>> cacheList,
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

    @Override
    public void deleteAllCached() {
        super.deleteAllCached();
        raceCache.deleteAllCached();
    }
}
