package com.room414.racingbets.dal.concrete.caching.factories;

import com.room414.racingbets.dal.abstraction.factories.CacheFactory;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;
import redis.clients.jedis.JedisPool;

import java.io.Closeable;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class RedisFactory implements CacheFactory, Closeable {
    private JedisPool jedisPool;

    public RedisFactory(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public RedisCache create() {
        return new RedisCache(jedisPool.getResource());
    }

    @Override
    public void close() {
        jedisPool.close();
    }
}
