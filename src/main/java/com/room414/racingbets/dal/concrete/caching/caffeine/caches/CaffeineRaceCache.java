package com.room414.racingbets.dal.concrete.caching.caffeine.caches;

import com.fasterxml.jackson.core.type.TypeReference;
import com.room414.racingbets.dal.abstraction.cache.BetCache;
import com.room414.racingbets.dal.abstraction.cache.ParticipantCache;
import com.room414.racingbets.dal.abstraction.cache.RaceCache;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.BaseCache;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.CachePool;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;
import com.room414.racingbets.dal.domain.entities.Race;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class CaffeineRaceCache extends BaseCache<Race> implements RaceCache {
    private static final TypeReference<Race> TYPE = new TypeReference<Race>() {};
    private static final TypeReference<List<Race>> LIST_TYPE = new TypeReference<List<Race>>() {};

    private ParticipantCache participantCache;
    private BetCache betCache;

    public CaffeineRaceCache(
            CachePool<Race> cachePool,
            RedisCache redisCache,
            BetCache betCache,
            ParticipantCache participantCache
    ) {
        super(cachePool, TYPE, LIST_TYPE, redisCache);
        this.participantCache = participantCache;
        this.betCache = betCache;
    }


    @Override
    public void deleteOneCached(String key) {
        super.deleteOneCached(key);
        participantCache.deleteThumbnail();
    }

    @Override
    public void deleteOddsCache(long raceId) {
        betCache.deleteOdds(raceId);
    }
}
