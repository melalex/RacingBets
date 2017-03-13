package com.room414.racingbets.dal.abstraction.factories;

import com.room414.racingbets.dal.abstraction.caching.DaoCache;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public interface CacheFactory {
    DaoCache create();
}
