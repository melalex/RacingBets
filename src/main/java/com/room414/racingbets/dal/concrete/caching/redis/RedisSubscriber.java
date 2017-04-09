package com.room414.racingbets.dal.concrete.caching.redis;

import com.github.benmanes.caffeine.cache.Cache;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.MainCachePool;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Map;

/**
 * @author Alexander Melashchenko
 * @version 1.0 17 Mar 2017
 */
public class RedisSubscriber extends JedisPubSub {
    static final String DELETE_CHANEL_ALL = "racing:bets:delete";
    static final String DELETE_CHANEL_FIELD = "racing:bets:delete:field";
    static final String UPDATE_CHANEL_ODDS = "racing:bets:update:odds";

    private Map<String, Cache> cacheByNamespaceMap;

    public RedisSubscriber(Map<String, Cache> cacheByNamespaceMap) {
        this.cacheByNamespaceMap = cacheByNamespaceMap;
    }

    // TODO: is good?
    public void subscribe(Jedis jedis) {
        String channel = "racing:bets:*";
        Runnable subscribe = () -> {
            jedis.psubscribe(this, channel);
            jedis.close();
        };
        Thread subscriberThread = new Thread(subscribe, "Subscriber");
        subscriberThread.setDaemon(true);
        subscriberThread.start();
    }

    public void unSubscribe() {
        this.unsubscribe();
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        switch (channel) {
            case DELETE_CHANEL_ALL:
                invalidateAll(message);
                break;
            case DELETE_CHANEL_FIELD:
                invalidateField(message);
                break;
            case UPDATE_CHANEL_ODDS:
                invalidateOdds(message);
                break;
        }
    }

    private void invalidateAll(String message) {
        Cache cache = cacheByNamespaceMap.get(message);
        if (cache != null) {
            cache.invalidateAll();
        }
    }

    private void invalidateField(String message) {
        String[] strings = message.split("@");
        Cache cache = cacheByNamespaceMap.get(strings[0]);
        if (cache != null) {
            cache.invalidate(strings[1]);
        }
    }

    private void invalidateOdds(String message) {
        Cache cache = cacheByNamespaceMap.get(MainCachePool.getOddsNamespace());

        if (cache != null) {
            cache.invalidate(message);
        }
    }
}
