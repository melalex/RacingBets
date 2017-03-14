package com.room414.racingbets.dal.concrete.caching.caffeine.caches;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.benmanes.caffeine.cache.Cache;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.infrastructure.Getter;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.BaseCache;
import com.room414.racingbets.dal.concrete.caching.redis.RedisBetCache;
import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;

import java.util.List;

import static com.room414.racingbets.dal.concrete.caching.redis.RedisBetCache.getOddsKey;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class BetCache extends BaseCache<Bet> {
    private static final String NAME_SPACE = "bet";
    private static final String LIST_NAME_SPACE = "bet:list";
    private static final String COUNT_NAME_SPACE = "bet:count";

    private static final TypeReference<Bet> TYPE = new TypeReference<Bet>() {};
    private static final TypeReference<List<Bet>> LIST_TYPE = new TypeReference<List<Bet>>() {};

    private RedisBetCache redisBetCache;
    private Cache<String, Odds> oddsCache;

    public BetCache(
            Cache<String, Bet> cache,
            Cache<String, List<Bet>> cacheList,
            Cache<String, Long> countCache,
            Cache<String, Odds> oddsCache,
            RedisBetCache redisCache
    ) {
        super(NAME_SPACE, LIST_NAME_SPACE, COUNT_NAME_SPACE, TYPE, LIST_TYPE, cache, cacheList, countCache, redisCache);
        this.redisBetCache = redisCache;
        this.oddsCache = oddsCache;
    }

    public Odds getOdds(Bet bet, Getter<Odds> getter) throws DalException {
        return CacheHelper.getCached(
                oddsCache,
                getOddsKey(NAME_SPACE, bet),
                () -> redisBetCache.getOdds(bet, getter)
        );
    }

    public void updateOdds(Bet bet) {
        oddsCache.invalidate(getOddsKey(NAME_SPACE, bet));
        redisBetCache.updateOdds(bet);
    }

    public void deleteOdds(long raceId) {
        oddsCache.invalidateAll();
        redisBetCache.deleteOdds(raceId);
    }
}
