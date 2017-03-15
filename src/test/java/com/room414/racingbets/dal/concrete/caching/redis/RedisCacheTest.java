package com.room414.racingbets.dal.concrete.caching.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;
import com.room414.racingbets.dal.infrastructure.factories.TestingRedisUnitOfWorkFactory;
import com.room414.racingbets.dal.resolvers.RedisParameterResolver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.room414.racingbets.dal.infrastructure.TestHelper.defaultAssertionFailMessage;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 15 Mar 2017
 */
@ExtendWith(RedisParameterResolver.class)
class RedisCacheTest {
    private static TestingRedisUnitOfWorkFactory factory;
    private static RedisUnitOfWork unitOfWork;
    private static RedisCache cache;

    @BeforeAll
    static void setUp(TestingRedisUnitOfWorkFactory factory) {
        RedisCacheTest.factory = factory;
        RedisCacheTest.unitOfWork = factory.create();
        RedisCacheTest.cache = unitOfWork.getRedisCache();
    }

    @AfterAll
    static void tearDown() throws Exception {
        unitOfWork.close();
        factory.close();
    }

    @Test
    void getCached() {
        String namespace = "test";
        String key = "get:cached:" + System.currentTimeMillis();
        Pair<String, Integer> target = new Pair<>("Cache", 1);

        Pair<String, Integer> result = cache.getCached(
                namespace, key, () -> target, new TypeReference<Pair<String, Integer>>() { }
        );

        assert target.equals(result) : defaultAssertionFailMessage(result, target);

        result = cache.getCached(
                namespace, key, () -> null, new TypeReference<Pair<String, Integer>>() { }
        );

        assert target.equals(result) : defaultAssertionFailMessage(result, target);
    }

    @Test
    void getCachedCount() {

    }

    @Test
    void delete() {

    }

    @Test
    void delete1() {

    }

}