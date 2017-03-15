package com.room414.racingbets.dal.concrete.caching.caffeine.caches;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.benmanes.caffeine.cache.Cache;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.BaseCache;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;
import com.room414.racingbets.dal.domain.entities.Race;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class RaceCache extends BaseCache<Race> {
    private static final String NAME_SPACE = "race:user";
    private static final String LIST_NAME_SPACE = "race:user:list";
    private static final String COUNT_NAME_SPACE = "race:user:count";

    private static final TypeReference<Race> TYPE = new TypeReference<Race>() {};
    private static final TypeReference<List<Race>> LIST_TYPE = new TypeReference<List<Race>>() {};

    private ParticipantCache participantCache;

    public RaceCache(
            Cache<String, Race> cache,
            Cache<String, List<Race>> cacheList,
            Cache<String, Long> countCache,
            ParticipantCache participantCache,
            RedisCache redisCache
    ) {
        super(NAME_SPACE, LIST_NAME_SPACE, COUNT_NAME_SPACE, TYPE, LIST_TYPE, cache, cacheList, countCache, redisCache);
        this.participantCache = participantCache;
    }


    @Override
    public void deleteOneCached(String key) {
        super.deleteOneCached(key);
        participantCache.deleteWhoAndWhen();
    }
}
