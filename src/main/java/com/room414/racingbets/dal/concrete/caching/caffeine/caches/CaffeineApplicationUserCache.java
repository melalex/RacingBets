package com.room414.racingbets.dal.concrete.caching.caffeine.caches;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.benmanes.caffeine.cache.Cache;
import com.room414.racingbets.dal.abstraction.cache.ApplicationUserCache;
import com.room414.racingbets.dal.abstraction.cache.BetCache;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.BaseCache;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;

import java.util.List;


/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class CaffeineApplicationUserCache extends BaseCache<ApplicationUser> implements ApplicationUserCache {
    private static final String NAME_SPACE = "application:user";
    private static final String LIST_NAME_SPACE = "application:user:list";
    private static final String COUNT_NAME_SPACE = "application:user:count";

    private static final TypeReference<ApplicationUser> TYPE = new TypeReference<ApplicationUser>() {};
    private static final TypeReference<List<ApplicationUser>> LIST_TYPE = new TypeReference<List<ApplicationUser>>() {};

    private BetCache betCache;

    public CaffeineApplicationUserCache(
            Cache<String, ApplicationUser> cache,
            Cache<String, List<ApplicationUser>> cacheList,
            Cache<String, Long> countCache,
            RedisCache redisCache,
            BetCache betCache
    ) {
        super(NAME_SPACE, LIST_NAME_SPACE, COUNT_NAME_SPACE, TYPE, LIST_TYPE, cache, cacheList, countCache, redisCache);
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
