package com.room414.racingbets.dal.concrete.caching.redis;

import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.infrastructure.Getter;
import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.math.BigDecimal;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class RedisBetCache extends RedisCache {
    private static final String PRIZE_POOL_KEY = "prize:pool:";
    private static final String EVENT_POOL_KEY = "event:pool:";
    private static final String COMMISSION_KEY = "commission:";


    public RedisBetCache(Jedis jedis) {
        super(jedis);
    }

    private String getHashKey(long raceId) {
        return "odds:" + raceId;
    }

    private String getOddsKey(String namespace, Bet bet) {
        StringBuilder builder = new StringBuilder(namespace);
        builder.append(bet.getBetType().toString());

        for (int i = 1, count = bet.getParticipants().size(); i <= count; i++) {
            builder.append(bet.getParticipantByPlace(i));
        }

        return builder.toString();
    }

    public Odds getOdds(Bet bet, Getter<Odds> getter) throws DalException {
        String hashKey = getHashKey(bet.getRaceId());
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

        Pipeline pipeline = jedis.pipelined();
        pipeline.hset(hashKey, prizePoolKey, odds.getPrizePool().toString());
        pipeline.hset(hashKey, eventPoolKey, odds.getEventPool().toString());
        pipeline.hset(hashKey, commissionKey, String.valueOf(odds.getCommission()));
        pipeline.sync();

        return odds;
    }

    public void updateOdds(Bet bet) {
        String hashKey = getHashKey(bet.getRaceId());
        String prizePoolKey = getOddsKey(PRIZE_POOL_KEY, bet);
        String eventPoolKey = getOddsKey(EVENT_POOL_KEY, bet);

        Pipeline pipeline = jedis.pipelined();
        pipeline.hincrByFloat(hashKey, prizePoolKey, bet.getBetSize().doubleValue());
        pipeline.hincrByFloat(hashKey, eventPoolKey, bet.getBetSize().doubleValue());
        pipeline.sync();
    }

    public void deleteOdds(long raceId) {
        getTransaction().del(getHashKey(raceId));
    }
}
