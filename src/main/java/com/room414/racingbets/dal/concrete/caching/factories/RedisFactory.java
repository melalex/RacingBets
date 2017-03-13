package com.room414.racingbets.dal.concrete.caching.factories;

import com.room414.racingbets.dal.concrete.caching.redis.BetCache;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;
import redis.clients.jedis.JedisPool;

import java.io.Closeable;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class RedisFactory implements Closeable {
    private JedisPool jedisPool;

    RedisFactory(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public RedisCache createCommonCache() {
        return new RedisCache(jedisPool.getResource());
    }

    public BetCache betCache() {
        return new BetCache(jedisPool.getResource());
    }

    @Override
    public void close() {
        jedisPool.close();
    }
}
