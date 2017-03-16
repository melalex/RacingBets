package com.room414.racingbets.dal.abstraction.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.room414.racingbets.dal.infrastructure.factories.TestCacheFactory;
import com.room414.racingbets.dal.resolvers.TestCacheFactoryParameterResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @author Alexander Melashchenko
 * @version 1.0 16 Mar 2017
 */
@ExtendWith(TestCacheFactoryParameterResolver.class)
class EntityCacheTest {
    TestCacheFactory cacheFactory;

    EntityCacheTest(TestCacheFactory cacheFactory) {
        this.cacheFactory = cacheFactory;
    }



    <T> void getOneCached(EntityCache<T> entityCache, Cache<String, T> cache) {

    }

    @Test
    void getManyCached() {

    }

    @Test
    void getCachedCount() {

    }

}