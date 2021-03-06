package com.room414.racingbets.dal.concrete.caching.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;
import com.room414.racingbets.dal.infrastructure.factories.TestingRedisFactory;
import com.room414.racingbets.resolvers.RedisParameterResolver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import redis.clients.jedis.Jedis;

import java.io.IOException;

import static com.room414.racingbets.dal.infrastructure.TestHelper.defaultAssertionFailMessage;

/**
 * @author Alexander Melashchenko
 * @version 1.0 15 Mar 2017
 */
@ExtendWith(RedisParameterResolver.class)
class RedisCacheTest {
    private static TestingRedisFactory factory;
    private static RedisUnitOfWork unitOfWork;
    private static RedisCache cache;

    @BeforeAll
    static void setUp(TestingRedisFactory factory) {
        RedisCacheTest.factory = factory;
    }

    @AfterAll
    static void tearDown() throws Exception {
        try(Jedis jedis = factory.getConnection()) {
            jedis.flushDB();
        }
        factory.close();
    }

    @BeforeEach
    void beforeEach() {
        unitOfWork = factory.create();
        cache = unitOfWork.getRedisCache();
    }

    @AfterEach
    void afterEach() throws Exception {
        unitOfWork.close();
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
        int target = (int) System.currentTimeMillis();

        int result = cache.getCachedCount(namespace, key, () -> target);

        assert target == result : defaultAssertionFailMessage(result, target);

        result = cache.getCachedCount(namespace, key, () -> -1);

        assert target == result : defaultAssertionFailMessage(result, target);
    }

    @Test
    void delete() throws IOException {
        String namespace = "test:delete";
        String key1 = "delete:key1:" + System.currentTimeMillis();
        String key2 = "delete:key2:" + System.currentTimeMillis();
        int target1 = (int) System.currentTimeMillis();
        int target2 = (int) System.currentTimeMillis();

        int result1 = cache.getCachedCount(namespace, key1, () -> target1);
        int result2 = cache.getCachedCount(namespace, key2, () -> target1);

        assert target1 == result1 : defaultAssertionFailMessage(result1, target1);
        assert target2 == result2 : defaultAssertionFailMessage(result2, target2);

        cache.delete(namespace);
        cache.commit();

        result1 = cache.getCachedCount(namespace, key1, () -> -1);
        result2 = cache.getCachedCount(namespace, key2, () -> -1);

        assert result1 == -1 : defaultAssertionFailMessage(result1, -1);
        assert result2 == -1 : defaultAssertionFailMessage(result2, -1);
    }

    @Test
    void deleteFromNamespace() throws IOException {
        String namespace = "test:delete";
        String key1 = "delete:key1:" + System.currentTimeMillis();
        String key2 = "delete:key2:" + System.currentTimeMillis();
        int target1 = (int) System.currentTimeMillis();
        int target2 = (int) System.currentTimeMillis();

        long result1 = cache.getCachedCount(namespace, key1, () -> target1);
        long result2 = cache.getCachedCount(namespace, key2, () -> target1);

        assert target1 == result1 : defaultAssertionFailMessage(result1, target1);
        assert target2 == result2 : defaultAssertionFailMessage(result2, target2);

        cache.delete(namespace, key1);
        cache.commit();

        result1 = cache.getCachedCount(namespace, key1, () -> -1);
        result2 = cache.getCachedCount(namespace, key2, () -> -1);

        assert result1 == -1 : defaultAssertionFailMessage(result1, -1);
        assert result2 == target2 : defaultAssertionFailMessage(result2, target2);
    }
}