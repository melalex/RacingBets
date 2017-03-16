package com.room414.racingbets.dal.abstraction.factories;

import com.room414.racingbets.dal.abstraction.cache.CachingUnitOfWork;

/**
 * @author Alexander Melashchenko
 * @version 1.0 16 Mar 2017
 */
public interface CachingUnitOfWorkFactory {
    CachingUnitOfWork create();
}
