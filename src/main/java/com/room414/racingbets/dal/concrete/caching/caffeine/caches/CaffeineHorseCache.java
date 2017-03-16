package com.room414.racingbets.dal.concrete.caching.caffeine.caches;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.benmanes.caffeine.cache.Cache;
import com.room414.racingbets.dal.abstraction.cache.HorseCache;
import com.room414.racingbets.dal.abstraction.cache.ParticipantCache;
import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.BaseCache;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class CaffeineHorseCache extends BaseCache<Horse> implements HorseCache {
    private static final String NAME_SPACE = "horse";
    private static final String LIST_NAME_SPACE = "horse:list";
    private static final String COUNT_NAME_SPACE = "horse:count";

    private static final TypeReference<Horse> TYPE = new TypeReference<Horse>() {};
    private static final TypeReference<List<Horse>> LIST_TYPE = new TypeReference<List<Horse>>() {};

    private ParticipantCache participantCache;

    public CaffeineHorseCache(
            Cache<String, Horse> cache,
            Cache<String, List<Horse>> cacheList,
            Cache<String, Long> countCache,
            RedisCache redisCache,
            ParticipantCache participantCache
    ) {
        super(NAME_SPACE, LIST_NAME_SPACE, COUNT_NAME_SPACE, TYPE, LIST_TYPE, cache, cacheList, countCache, redisCache);
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
