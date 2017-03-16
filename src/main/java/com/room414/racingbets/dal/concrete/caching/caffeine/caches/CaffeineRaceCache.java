package com.room414.racingbets.dal.concrete.caching.caffeine.caches;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.benmanes.caffeine.cache.Cache;
import com.room414.racingbets.dal.abstraction.cache.BetCache;
import com.room414.racingbets.dal.abstraction.cache.ParticipantCache;
import com.room414.racingbets.dal.abstraction.cache.RaceCache;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.BaseCache;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;
import com.room414.racingbets.dal.domain.entities.Race;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class CaffeineRaceCache extends BaseCache<Race> implements RaceCache {
    private static final String NAME_SPACE = "race:user";
    private static final String LIST_NAME_SPACE = "race:user:list";
    private static final String COUNT_NAME_SPACE = "race:user:count";

    private static final TypeReference<Race> TYPE = new TypeReference<Race>() {};
    private static final TypeReference<List<Race>> LIST_TYPE = new TypeReference<List<Race>>() {};

    private ParticipantCache participantCache;
    private BetCache betCache;

    public CaffeineRaceCache(
            Cache<String, Race> cache,
            Cache<String, List<Race>> cacheList,
            Cache<String, Long> countCache,
            ParticipantCache participantCache,
            BetCache betCache,
            RedisCache redisCache
    ) {
        super(NAME_SPACE, LIST_NAME_SPACE, COUNT_NAME_SPACE, TYPE, LIST_TYPE, cache, cacheList, countCache, redisCache);
        this.participantCache = participantCache;
        this.betCache = betCache;
    }


    @Override
    public void deleteOneCached(String key) {
        super.deleteOneCached(key);
        participantCache.deleteWhoAndWhen();
    }

    @Override
    public void deleteOddsCache(long raceId) {
        betCache.deleteOdds(raceId);
    }
}
