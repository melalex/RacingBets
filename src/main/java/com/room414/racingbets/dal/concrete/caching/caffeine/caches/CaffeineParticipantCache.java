package com.room414.racingbets.dal.concrete.caching.caffeine.caches;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.benmanes.caffeine.cache.Cache;
import com.room414.racingbets.dal.abstraction.cache.ParticipantCache;
import com.room414.racingbets.dal.abstraction.cache.RaceCache;
import com.room414.racingbets.dal.abstraction.infrastructure.Getter;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.BaseCache;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.CachePool;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;
import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.domain.entities.RaceParticipantThumbnail;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class CaffeineParticipantCache extends BaseCache<Participant> implements ParticipantCache {
    private static final TypeReference<Participant> TYPE = new TypeReference<Participant>() {};
    private static final TypeReference<List<Participant>> LIST_TYPE = new TypeReference<List<Participant>>() {};
    private static final TypeReference<List<RaceParticipantThumbnail>> WHO_AND_WHEN
            = new TypeReference<List<RaceParticipantThumbnail>>() {};

    private String whoAndWhenCacheNameSpace;
    private RaceCache raceCache;
    private Cache<String, List<RaceParticipantThumbnail>> whoAndWhenCache;

    public CaffeineParticipantCache(
            CachePool<Participant> participantCachePool,
            CachePool<RaceParticipantThumbnail> whoAndWhenCachePool,
            RedisCache redisCache,
            RaceCache raceCache
    ) {
        super(participantCachePool, TYPE, LIST_TYPE, redisCache);
        this.whoAndWhenCache = whoAndWhenCachePool.getListCache();
        this.whoAndWhenCacheNameSpace = whoAndWhenCachePool.getListCacheNamespace();
        this.raceCache = raceCache;
    }

    @Override
    public List<RaceParticipantThumbnail> getThumbnailCached(
            String key, Getter<List<RaceParticipantThumbnail>> getter
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
        redisCache.delete(whoAndWhenCacheNameSpace);
    }

    @Override
    public void deleteManyCached() {
        super.deleteManyCached();
        redisCache.delete(whoAndWhenCacheNameSpace);
    }

    @Override
    public void deleteAllCached() {
        super.deleteAllCached();
        raceCache.deleteAllCached();
        redisCache.delete(whoAndWhenCacheNameSpace);
    }

    @Override
    public void deleteThumbnail() {
        redisCache.delete(whoAndWhenCacheNameSpace);
    }
}
