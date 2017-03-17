package com.room414.racingbets.dal.concrete.caching.redis;

import redis.clients.jedis.JedisPubSub;

/**
 * @author Alexander Melashchenko
 * @version 1.0 17 Mar 2017
 */
public class RedisSubscriber extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        super.onMessage(channel, message);
    }
}
