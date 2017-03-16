package com.room414.racingbets.dal.abstraction.cache;

import com.room414.racingbets.dal.abstraction.infrastructure.Getter;
import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;

import static com.room414.racingbets.dal.concrete.caching.redis.RedisBetCache.getOddsKey;

/**
 * @author Alexander Melashchenko
 * @version 1.0 16 Mar 2017
 */
public interface BetCache extends EntityCache<Bet> {
    Odds getOdds(Bet bet, Getter<Odds> getter);
    void updateOdds(Bet bet);
    void deleteOdds(long raceId);
}
