package com.room414.racingbets.dal.concrete.caching.factories;

import com.room414.racingbets.dal.concrete.caching.redis.RedisUnitOfWork;
import redis.clients.jedis.JedisPool;


/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class RedisUnitOfWorkFactory {
    private JedisPool jedisPool;

    public RedisUnitOfWorkFactory(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public RedisUnitOfWork create() {
        return new RedisUnitOfWork(jedisPool.getResource());
    }
}
