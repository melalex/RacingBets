package com.room414.racingbets.dal.abstraction.cache;

import com.room414.racingbets.dal.domain.entities.Race;

/**
 * @author Alexander Melashchenko
 * @version 1.0 16 Mar 2017
 */
public interface RaceCache extends EntityCache<Race> {
    void deleteOddsCache(long raceId);
}
