package com.room414.racingbets.dal.concrete.caching.redis;

import redis.clients.jedis.Jedis;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class RedisUnitOfWork implements AutoCloseable {
    private Jedis jedis;
    private RedisBetCache redisBetCache;
    private RedisCache redisCache;

    public RedisUnitOfWork(Jedis jedis) {
        this.jedis = jedis;
    }

    public RedisBetCache getRedisBetCache() {
        if (redisBetCache == null) {
            redisBetCache = new RedisBetCache(jedis);
        }
        return redisBetCache;
    }

    public RedisCache getRedisCache() {
        if (redisCache == null) {
            redisCache = new RedisCache(jedis);
        }
        return redisCache;
    }

    public void commit() {
        redisBetCache.commit();
        redisCache.commit();
    }

    public void rollback() {
        redisBetCache.rollback();
        redisCache.rollback();
    }

    @Override
    public void close() throws Exception {
        jedis.close();
    }
}
