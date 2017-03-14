package com.room414.racingbets.dal.concrete.caching.caffeine.caches;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.benmanes.caffeine.cache.Cache;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.BaseCache;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;

import java.util.List;


/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class ApplicationUserCache extends BaseCache<ApplicationUser> {
    private static final String NAME_SPACE = "application:user";
    private static final String LIST_NAME_SPACE = "application:user:list";
    private static final String COUNT_KEY = "application:user:count";

    private static final TypeReference<ApplicationUser> TYPE = new TypeReference<ApplicationUser>() {};
    private static final TypeReference<List<ApplicationUser>> LIST_TYPE = new TypeReference<List<ApplicationUser>>() {};


    public ApplicationUserCache(
            Cache<String, ApplicationUser> cache,
            Cache<String, List<ApplicationUser>> cacheList,
            Cache<String, Long> countCache,
            RedisCache redisCache
    ) {
        super(NAME_SPACE, LIST_NAME_SPACE, COUNT_KEY, TYPE, LIST_TYPE, cache, cacheList, countCache, redisCache);
    }
}
