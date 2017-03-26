package com.room414.racingbets.dal.concrete.caching.caffeine.caches;

import com.fasterxml.jackson.core.type.TypeReference;
import com.room414.racingbets.dal.abstraction.cache.ParticipantCache;
import com.room414.racingbets.dal.abstraction.cache.RaceCache;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.BaseCache;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.CachePool;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;
import com.room414.racingbets.dal.domain.entities.Participant;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class CaffeineParticipantCache extends BaseCache<Participant> implements ParticipantCache {
    private static final TypeReference<Participant> TYPE = new TypeReference<Participant>() {};
    private static final TypeReference<List<Participant>> LIST_TYPE = new TypeReference<List<Participant>>() {};

    private RaceCache raceCache;

    public CaffeineParticipantCache(
            CachePool<Participant> participantCachePool,
            RedisCache redisCache,
            RaceCache raceCache
    ) {
        super(participantCachePool, TYPE, LIST_TYPE, redisCache);
        this.raceCache = raceCache;
    }


    @Override
    public void deleteOneCached(String key) {
        super.deleteOneCached(key);
        raceCache.deleteAllCached();
    }

    @Override
    public void deleteManyCached() {
        super.deleteManyCached();
    }

    @Override
    public void deleteAllCached() {
        super.deleteAllCached();
        raceCache.deleteAllCached();
    }
}
