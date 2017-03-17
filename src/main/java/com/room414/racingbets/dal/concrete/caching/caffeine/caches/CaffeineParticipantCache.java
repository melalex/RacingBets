package com.room414.racingbets.dal.concrete.caching.caffeine.caches;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.benmanes.caffeine.cache.Cache;
import com.room414.racingbets.dal.abstraction.cache.ParticipantCache;
import com.room414.racingbets.dal.abstraction.cache.RaceCache;
import com.room414.racingbets.dal.abstraction.infrastructure.Getter;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.BaseCache;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.CachePool;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;
import com.room414.racingbets.dal.domain.entities.Participant;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class CaffeineParticipantCache extends BaseCache<Participant> implements ParticipantCache {
    private static final TypeReference<Participant> TYPE = new TypeReference<Participant>() {};
    private static final TypeReference<List<Participant>> LIST_TYPE = new TypeReference<List<Participant>>() {};
    private static final TypeReference<List<Pair<Participant, Timestamp>>> WHO_AND_WHEN
            = new TypeReference<List<Pair<Participant, Timestamp>>>() {};

    private String whoAndWhenCacheNameSpace;
    private RaceCache raceCache;
    private Cache<String, List<Pair<Participant, Timestamp>>> whoAndWhenCache;

    public CaffeineParticipantCache(
            CachePool<Participant> participantCachePool,
            CachePool<Pair<Participant, Timestamp>> whoAndWhenCachePool,
            RedisCache redisCache,
            RaceCache raceCache
    ) {
        super(participantCachePool, TYPE, LIST_TYPE, redisCache);
        this.whoAndWhenCache = whoAndWhenCachePool.getListCache();
        this.whoAndWhenCacheNameSpace = whoAndWhenCachePool.getListCacheNamespace();
        this.raceCache = raceCache;
    }

    @Override
    public List<Pair<Participant, Timestamp>> getWhoAndWhenCached(
            String key, Getter<List<Pair<Participant, Timestamp>>> getter
    ) {
        return whoAndWhenCache.get(
                key,
                k -> redisCache.getCached(whoAndWhenCacheNameSpace, key, getter, WHO_AND_WHEN)
        );
    }

    @Override
    public void deleteOneCached(String key) {
        super.deleteOneCached(key);
        raceCache.deleteAllCached();
        whoAndWhenCache.invalidateAll();
        redisCache.delete(whoAndWhenCacheNameSpace);
    }

    @Override
    public void deleteManyCached() {
        super.deleteManyCached();
        whoAndWhenCache.invalidateAll();
        redisCache.delete(whoAndWhenCacheNameSpace);
    }

    @Override
    public void deleteAllCached() {
        super.deleteAllCached();
        raceCache.deleteAllCached();
        whoAndWhenCache.invalidateAll();
        redisCache.delete(whoAndWhenCacheNameSpace);
    }

    @Override
    public void deleteWhoAndWhen() {
        whoAndWhenCache.invalidateAll();
        redisCache.delete(whoAndWhenCacheNameSpace);
    }
}
