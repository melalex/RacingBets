package com.room414.racingbets.dal.concrete.caching.factories;

import com.room414.racingbets.dal.concrete.caching.caffeine.CachingUnitOfWork;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.MainCachePool;

/**
 * @author Alexander Melashchenko
 * @version 1.0 15 Mar 2017
 */
public class CachingUnitOfWorkFactory {
    private RedisUnitOfWorkFactory redisUnitOfWorkFactory;
    private MainCachePool mainCachePool;

    public CachingUnitOfWorkFactory(RedisUnitOfWorkFactory redisUnitOfWorkFactory, MainCachePool mainCachePool) {
        this.redisUnitOfWorkFactory = redisUnitOfWorkFactory;
        this.mainCachePool = mainCachePool;
    }

    public CachingUnitOfWork create() {
        return new CachingUnitOfWork(mainCachePool, redisUnitOfWorkFactory.create());
    }
}
