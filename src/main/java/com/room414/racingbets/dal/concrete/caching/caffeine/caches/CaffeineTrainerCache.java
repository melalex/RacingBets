package com.room414.racingbets.dal.concrete.caching.caffeine.caches;

import com.fasterxml.jackson.core.type.TypeReference;
import com.room414.racingbets.dal.abstraction.cache.HorseCache;
import com.room414.racingbets.dal.abstraction.cache.TrainerCache;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.BaseCache;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.CachePool;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;
import com.room414.racingbets.dal.domain.entities.Trainer;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class CaffeineTrainerCache extends BaseCache<Trainer> implements TrainerCache {
    private static final TypeReference<Trainer> TYPE = new TypeReference<Trainer>() {};
    private static final TypeReference<List<Trainer>> LIST_TYPE = new TypeReference<List<Trainer>>() {};

    private HorseCache horseCache;

    public CaffeineTrainerCache(
            CachePool<Trainer> cachePool,
            RedisCache redisCache,
            HorseCache horseCache
    ) {
        super(cachePool, TYPE, LIST_TYPE, redisCache);
        this.horseCache = horseCache;
    }

    @Override
    public void deleteOneCached(String key) {
        super.deleteOneCached(key);
        horseCache.deleteAllCached();
    }
}
