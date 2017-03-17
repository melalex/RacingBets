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
    private static final String DELETE_CHANEL_ALL = "racing:bets:delete";
    private static final String DELETE_CHANEL_FIELD = "racing:bets:delete:field";
    private static final String DELETE_CHANEL_ODDS = "racing:bets:delete:odds";
    private static final String UPDATE_CHANEL_ODDS = "racing:bets:update:odds";

    private Map<String, Cache> cacheByNamespaceMap;

    public RedisSubscriber(Map<String, Cache> cacheByNamespaceMap) {
        this.cacheByNamespaceMap = cacheByNamespaceMap;
    }

    public static String getDeleteChanelAll() {
        return DELETE_CHANEL_ALL;
    }

    public static String getDeleteChanelField() {
        return DELETE_CHANEL_FIELD;
    }

    public static String getDeleteChanelOdds() {
        return DELETE_CHANEL_ODDS;
    }

    public static String getUpdateChanelOdds() {
        return UPDATE_CHANEL_ODDS;
    }

    // TODO: is good?
    public void subscribe(Jedis jedis) {
        String channel = "racing:bets:*";
        Runnable subscribe = () -> jedis.subscribe(this, channel);
        Thread subscriberTread = new Thread(subscribe, "Subscriber");
        subscriberTread.setDaemon(true);
        subscriberTread.start();
    }

    @Override
    public void onMessage(String channel, String message) {
        super.onMessage(channel, message);

        switch (channel) {
            case DELETE_CHANEL_ALL:
                invalidateAll(message);
                break;
            case DELETE_CHANEL_FIELD:
                invalidateField(message);
                break;
            case DELETE_CHANEL_ODDS:
                invalidateAll(MainCachePool.getOddsNamespace());
                break;
            case UPDATE_CHANEL_ODDS:
                invalidateOdds(message);
                break;
        }
    }

    private void invalidateAll(String message) {
        cacheByNamespaceMap.get(message).invalidateAll();
    }

    // TODO: use stream
    private void invalidateField(String message) {
        String[] strings = message.split("@");
        String namespace = strings[0];
        String field = strings[1];

        cacheByNamespaceMap.get(namespace).invalidate(field);
    }

    private void invalidateOdds(String message) {
        cacheByNamespaceMap.get(MainCachePool.getOddsNamespace()).invalidate(message);
    }
}
