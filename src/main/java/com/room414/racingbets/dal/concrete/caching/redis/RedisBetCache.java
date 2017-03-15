package com.room414.racingbets.dal.concrete.caching.redis;

import com.room414.racingbets.dal.abstraction.infrastructure.Getter;
import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.math.BigDecimal;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class RedisBetCache extends RedisCache {
    private static final String PRIZE_POOL_KEY = "prize:pool:";
    private static final String EVENT_POOL_KEY = "event:pool:";
    private static final String COMMISSION_KEY = "commission:";


    RedisBetCache(Jedis jedis) {
        super(jedis);
    }

    private String getHashKey(long raceId) {
        return "odds:" + raceId;
    }

    public static String getOddsKey(String namespace, Bet bet) {
        StringBuilder builder = new StringBuilder(namespace);
        builder.append(bet.getBetType().toString());

        for (int i = 1, count = bet.getParticipants().size(); i <= count; i++) {
            builder.append(bet.getParticipantByPlace(i));
        }

        return builder.toString();
    }

    public Odds getOdds(Bet bet, Getter<Odds> getter) {
        String hashKey = getHashKey(bet.getRaceId());
        String prizePoolKey = getOddsKey(PRIZE_POOL_KEY, bet);
        String eventPoolKey = getOddsKey(EVENT_POOL_KEY, bet);
        String commissionKey = getOddsKey(COMMISSION_KEY, bet);

        Transaction oddsTransaction = jedis.multi();
        Response<String> prizePoolResponse = oddsTransaction.hget(hashKey, prizePoolKey);
        Response<String> eventPoolResponse = oddsTransaction.hget(hashKey, eventPoolKey);
        Response<String> commissionResponse = oddsTransaction.hget(hashKey, commissionKey);
        oddsTransaction.exec();

        String prizePoolString = prizePoolResponse.get();
        String eventPoolString = eventPoolResponse.get();
        String commissionString = commissionResponse.get();

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
