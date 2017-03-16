package com.room414.racingbets.dal.concrete.caching.factories;

import com.room414.racingbets.dal.abstraction.factories.CachingUnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.caching.caffeine.CaffeineCachingUnitOfWork;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.MainCachePool;

/**
 * @author Alexander Melashchenko
 * @version 1.0 15 Mar 2017
 */
public class CaffeineCachingUnitOfWorkFactory implements CachingUnitOfWorkFactory {
    private RedisUnitOfWorkFactory redisUnitOfWorkFactory;
    private MainCachePool mainCachePool;

    public CaffeineCachingUnitOfWorkFactory(RedisUnitOfWorkFactory redisUnitOfWorkFactory, MainCachePool mainCachePool) {
        this.redisUnitOfWorkFactory = redisUnitOfWorkFactory;
        this.mainCachePool = mainCachePool;
    }

    @Override
    public CaffeineCachingUnitOfWork create() {
        return new CaffeineCachingUnitOfWork(mainCachePool, redisUnitOfWorkFactory.create());
    }
}
