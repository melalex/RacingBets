package com.room414.racingbets.dal.concrete.caching.redis;

import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class BetCache extends RedisCache {
    private static final String PRIZE_POOL_KEY = "prize:pool:";
    private static final String EVENT_POOL_KEY = "event:pool:";
    private static final String COMMISSION_KEY = "commission:";

    public BetCache(Jedis jedis) {
        super(jedis);
    }

    private String getHashKey(Bet bet) {
        return "odds:" + bet.getRaceId();
    }

    private String getOddsKey(String namespace, Bet bet) {
        StringBuilder builder = new StringBuilder(namespace);
        builder.append(bet.getBetType().toString());

        for (int i = 1, count = bet.getParticipants().size(); i <= count; i++) {
            builder.append(bet.getParticipantByPlace(i));
        }

        return builder.toString();
    }

    public Odds getOdds(Bet bet, Callable<Odds> getter) throws Exception {
        String hashKey = getHashKey(bet);
        String prizePoolKey = getOddsKey(PRIZE_POOL_KEY, bet);
        String eventPoolKey = getOddsKey(EVENT_POOL_KEY, bet);
        String commissionKey = getOddsKey(COMMISSION_KEY, bet);

        String prizePoolString = jedis.hget(hashKey, prizePoolKey);
        String eventPoolString = jedis.hget(hashKey, eventPoolKey);
        String commissionString = jedis.hget(hashKey, commissionKey);

        if (prizePoolString != null && eventPoolString != null && commissionString != null) {
            BigDecimal prizePool = new BigDecimal(prizePoolString);
            BigDecimal eventPool = new BigDecimal(prizePoolString);
            double commission = Double.valueOf(prizePoolString);

            return new Odds(prizePool, eventPool, commission);
        }

        Odds odds = getter.call();

        jedis.hset(hashKey, prizePoolKey, odds.getPrizePool().toString());
        jedis.hset(hashKey, eventPoolKey, odds.getEventPool().toString());
        jedis.hset(hashKey, commissionKey, String.valueOf(odds.getCommission()));

        return odds;
    }

    public void updateOdds(Bet bet) {
        String hashKey = getHashKey(bet);
        String prizePoolKey = getOddsKey(PRIZE_POOL_KEY, bet);
        String eventPoolKey = getOddsKey(EVENT_POOL_KEY, bet);

        jedis.hincrByFloat(hashKey, prizePoolKey, bet.getBetSize().doubleValue());
        jedis.hincrByFloat(hashKey, eventPoolKey, bet.getBetSize().doubleValue());
    }
}
