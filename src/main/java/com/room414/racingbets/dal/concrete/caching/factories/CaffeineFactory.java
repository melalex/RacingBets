package com.room414.racingbets.dal.concrete.caching.factories;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.room414.racingbets.dal.abstraction.factories.CacheFactory;
import com.room414.racingbets.dal.concrete.caching.caffeine.CaffeineCache;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class CaffeineFactory implements CacheFactory {
    private Cache<String, Object> cache;
    private CacheFactory lowLevelCacheFactory;

    private CaffeineFactory(Cache<String, Object> cache, CacheFactory lowLevelCacheFactory) {
        this.cache = cache;
        this.lowLevelCacheFactory = lowLevelCacheFactory;
    }

    static CaffeineFactory createFactory(CacheFactory lowLevelCacheFactory) {
        Cache<String, Object> cache = Caffeine.newBuilder().build();
        return new CaffeineFactory(cache, lowLevelCacheFactory);
    }

    @Override
    public CaffeineCache create() {
        return new CaffeineCache(cache, lowLevelCacheFactory.create());
    }
}
