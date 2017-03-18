package com.room414.racingbets.dal.concrete.caching.caffeine.caches;

import com.fasterxml.jackson.core.type.TypeReference;
import com.room414.racingbets.dal.abstraction.cache.HorseCache;
import com.room414.racingbets.dal.abstraction.cache.ParticipantCache;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.BaseCache;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.CachePool;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;
import com.room414.racingbets.dal.domain.entities.Horse;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class CaffeineHorseCache extends BaseCache<Horse> implements HorseCache {
    private static final TypeReference<Horse> TYPE = new TypeReference<Horse>() {};
    private static final TypeReference<List<Horse>> LIST_TYPE = new TypeReference<List<Horse>>() {};

    private ParticipantCache participantCache;

    public CaffeineHorseCache(
            CachePool<Horse> horseCachePool,
            ParticipantCache participantCache,
            RedisCache redisCache
    ) {
        super(horseCachePool,  TYPE, LIST_TYPE, redisCache);
        this.participantCache = participantCache;
    }

    @Override
    public void deleteOneCached(String key) {
        super.deleteOneCached(key);
        participantCache.deleteAllCached();
    }

    @Override
    public void deleteAllCached() {
        super.deleteAllCached();
        participantCache.deleteAllCached();
    }
}
