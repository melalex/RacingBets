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
        String namespace = "test";
        String key = "get:cached:count:" + System.currentTimeMillis();
        long target = System.currentTimeMillis();

        long result = cache.getCachedCount(namespace, key, () -> target);

        assert target == result : defaultAssertionFailMessage(result, target);

        result = cache.getCachedCount(namespace, key, () -> (long) -1);

        assert target == result : defaultAssertionFailMessage(result, target);
    }

    @Test
    void delete() {
        String namespace = "test:delete";
        String key1 = "delete:key1:" + System.currentTimeMillis();
        String key2 = "delete:key2:" + System.currentTimeMillis();
        long target1 = System.currentTimeMillis();
        long target2 = System.currentTimeMillis();

        long result1 = cache.getCachedCount(namespace, key1, () -> target1);
        long result2 = cache.getCachedCount(namespace, key2, () -> target1);

        assert target1 == result1 : defaultAssertionFailMessage(result1, target1);
        assert target2 == result2 : defaultAssertionFailMessage(result2, target2);

        cache.delete(namespace);
        cache.commit();

        result1 = cache.getCachedCount(namespace, key1, () -> (long) -1);
        result2 = cache.getCachedCount(namespace, key2, () -> (long) -1);

        assert result1 == -1 : defaultAssertionFailMessage(result1, -1);
        assert result2 == -1 : defaultAssertionFailMessage(result2, -1);
    }

    @Test
    void deleteFromNamespace() {
        String namespace = "test:delete";
        String key1 = "delete:key1:" + System.currentTimeMillis();
        String key2 = "delete:key2:" + System.currentTimeMillis();
        long target1 = System.currentTimeMillis();
        long target2 = System.currentTimeMillis();

        long result1 = cache.getCachedCount(namespace, key1, () -> target1);
        long result2 = cache.getCachedCount(namespace, key2, () -> target1);

        assert target1 == result1 : defaultAssertionFailMessage(result1, target1);
        assert target2 == result2 : defaultAssertionFailMessage(result2, target2);

        cache.delete(namespace, key1);
        cache.commit();

        result1 = cache.getCachedCount(namespace, key1, () -> (long) -1);
        result2 = cache.getCachedCount(namespace, key2, () -> (long) -1);

        assert result1 == -1 : defaultAssertionFailMessage(result1, target1);
        assert result2 == target2 : defaultAssertionFailMessage(result2, target2);
    }
}