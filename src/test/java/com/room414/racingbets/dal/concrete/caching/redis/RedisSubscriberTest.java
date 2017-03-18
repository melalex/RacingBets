package com.room414.racingbets.dal.concrete.caching.redis;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.MainCachePool;
import com.room414.racingbets.dal.infrastructure.factories.TestingRedisFactory;
import com.room414.racingbets.dal.resolvers.RedisParameterResolver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 17 Mar 2017
 */
@ExtendWith(RedisParameterResolver.class)
class RedisSubscriberTest {
    private static TestingRedisFactory redisFactory;
    private static RedisSubscriber redisSubscriber;
    private static Map<String, Cache> map = new HashMap<>();

    @BeforeAll
    static void setUp(TestingRedisFactory redisFactory) {
        RedisSubscriberTest.redisFactory = redisFactory;
        RedisSubscriberTest.redisSubscriber = new RedisSubscriber(map);
        RedisSubscriberTest.redisSubscriber.subscribe(redisFactory.getConnection());
    }

    @BeforeEach
    void setUp() {
        Cache<String, Integer> cache = Caffeine.<String, Integer>newBuilder().build();
        cache.put("cache:1", 1);
        cache.put("cache:2", 1);
        cache.put("cache:3", 1);

        Cache<String, Integer> oddsCache = Caffeine.<String, Integer>newBuilder().build();
        oddsCache.put("odds:cache:1", 1);
        oddsCache.put("odds:cache:1", 1);
        oddsCache.put("odds:cache:1", 1);

        map.clear();
        map.put("cache", cache);
        map.put(MainCachePool.getOddsNamespace(), oddsCache);
    }

    @AfterAll
    static void tearDown() throws Exception {
        RedisSubscriberTest.redisFactory.close();
    }

    @Test
    void invalidateAll() throws InterruptedException {
        String toDelete = "cache";
        Jedis jedis = redisFactory.getConnection();

        jedis.publish(RedisSubscriber.DELETE_CHANEL_ALL, toDelete);
        Thread.sleep(100);

        assert map.get(toDelete).getIfPresent("cache:1") == null : "cache:1 is valid!";
        assert map.get(toDelete).getIfPresent("cache:2") == null : "cache:2 is valid!";
        assert map.get(toDelete).getIfPresent("cache:3") == null : "cache:3 is valid!";
    }

    @Test
    void invalidateField() throws InterruptedException {
        String namespace = "cache";
        String field = "cache:1";
        Jedis jedis = redisFactory.getConnection();

        jedis.publish(RedisSubscriber.DELETE_CHANEL_FIELD, namespace + "@" + field);
        Thread.sleep(100);

        assert map.get(namespace).getIfPresent(field) == null : "Cache is valid!";
    }

    @Test
    void invalidateOdds() throws InterruptedException {
        String message = "odds:cache:1";
        Jedis jedis = redisFactory.getConnection();

        jedis.publish(RedisSubscriber.UPDATE_CHANEL_ODDS, message);
        Thread.sleep(100);

        assert map.get(MainCachePool.getOddsNamespace()).getIfPresent(message) == null : "Cache is valid!";
    }
}