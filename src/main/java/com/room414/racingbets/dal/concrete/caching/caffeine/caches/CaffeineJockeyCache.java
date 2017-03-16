package com.room414.racingbets.dal.concrete.caching.caffeine.caches;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.benmanes.caffeine.cache.Cache;
import com.room414.racingbets.dal.abstraction.cache.JockeyCache;
import com.room414.racingbets.dal.abstraction.cache.ParticipantCache;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.BaseCache;
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

    private ParticipantCache participantCache;

    public CaffeineJockeyCache(
            Cache<String, Jockey> cache,
            Cache<String, List<Jockey>> cacheList,
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
}
