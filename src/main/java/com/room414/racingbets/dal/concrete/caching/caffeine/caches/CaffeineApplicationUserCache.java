package com.room414.racingbets.dal.concrete.caching.caffeine.caches;

import com.fasterxml.jackson.core.type.TypeReference;
import com.room414.racingbets.dal.abstraction.cache.ApplicationUserCache;
import com.room414.racingbets.dal.abstraction.cache.BetCache;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.BaseCache;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.CachePool;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;

import java.util.List;


/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class CaffeineApplicationUserCache extends BaseCache<ApplicationUser> implements ApplicationUserCache {
    private static final TypeReference<ApplicationUser> TYPE = new TypeReference<ApplicationUser>() {};
    private static final TypeReference<List<ApplicationUser>> LIST_TYPE = new TypeReference<List<ApplicationUser>>() {};

    private BetCache betCache;

    public CaffeineApplicationUserCache(
            CachePool<ApplicationUser> cachePool,
            RedisCache redisCache,
            BetCache betCache
    ) {
        super(cachePool, TYPE, LIST_TYPE, redisCache);
        this.betCache = betCache;
    }

    @Override
    public void deleteOneCached(String key) {
        super.deleteOneCached(key);
        betCache.deleteAllCached();
    }

    @Override
    public void deleteAllCached() {
        super.deleteAllCached();
        betCache.deleteAllCached();
    }
}
