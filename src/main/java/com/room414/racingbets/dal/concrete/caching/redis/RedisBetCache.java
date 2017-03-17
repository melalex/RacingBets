package com.room414.racingbets.dal.concrete.caching.redis;

import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.infrastructure.Getter;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.MainCachePool;
import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class RedisBetCache extends RedisCache {
    private static final String PRIZE_POOL_KEY = "prize:pool:";
    private static final String EVENT_POOL_KEY = "event:pool:";
    private static final String COMMISSION_KEY = "commission:";

    private List<Bet> oddsToUpdate;

    private List<Bet> getOddsToUpdate() {
        if (oddsToUpdate == null) {
            oddsToUpdate = new LinkedList<>();
        }
        return oddsToUpdate;
    }

    RedisBetCache(Jedis jedis) {
        super(jedis);
    }

    public static String getHashKey(long raceId) {
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

        try (Transaction oddsTransaction = jedis.multi()) {
            Response<String> prizePoolResponse = oddsTransaction.hget(hashKey, prizePoolKey);
            Response<String> eventPoolResponse = oddsTransaction.hget(hashKey, eventPoolKey);
            Response<String> commissionResponse = oddsTransaction.hget(hashKey, commissionKey);
            oddsTransaction.exec();

            String prizePoolString = prizePoolResponse.get();
            String eventPoolString = eventPoolResponse.get();
            String commissionString = commissionResponse.get();

            if (prizePoolString != null && eventPoolString != null && commissionString != null) {
                BigDecimal prizePool = new BigDecimal(prizePoolString);
                BigDecimal eventPool = new BigDecimal(eventPoolString);
                double commission = Double.valueOf(commissionString);

                return new Odds(prizePool, eventPool, commission);
            }
        } catch (IOException e) {
            String message = "Can't close get odds transaction";
            throw new DalException(message, e);
        }

        Odds odds = getter.call();

        if (odds == null) {
            String message = "Getter should return value that != null";
            throw new IllegalArgumentException(message);
        }

        try (Transaction transaction = jedis.multi()) {
            transaction.hset(hashKey, prizePoolKey, odds.getPrizePool().toString());
            transaction.hset(hashKey, eventPoolKey, odds.getEventPool().toString());
            transaction.hset(hashKey, commissionKey, String.valueOf(odds.getCommission()));
            transaction.exec();
        } catch (IOException e) {
            String message = "Can't close set odds transaction";
            throw new DalException(message, e);
        }

        return odds;
    }

    public void updateOdds(Bet bet) {
        getOddsToUpdate().add(bet);
    }

    public void deleteOdds(long raceId) {
        getToDelete().add(MainCachePool.getOddsNamespace());
    }

    @Override
    void commit() throws IOException {
        super.commit();

        if (oddsToUpdate == null || oddsToUpdate.isEmpty()) {
            return;
        }

        try (Pipeline pipeline = jedis.pipelined()) {
            String hashKey;
            String prizePoolKey;
            String eventPoolKey;
            String message;

            for (Bet bet : oddsToUpdate) {
                hashKey = getHashKey(bet.getRaceId());
                prizePoolKey = getOddsKey(PRIZE_POOL_KEY, bet);
                eventPoolKey = getOddsKey(EVENT_POOL_KEY, bet);
                message = getOddsKey(getHashKey(bet.getId()), bet);

                pipeline.multi();
                pipeline.hincrByFloat(hashKey, prizePoolKey, bet.getBetSize().doubleValue());
                pipeline.hincrByFloat(hashKey, eventPoolKey, bet.getBetSize().doubleValue());
                pipeline.publish(RedisSubscriber.UPDATE_CHANEL_ODDS, message);
                pipeline.exec();
            }
            pipeline.sync();
        } catch (IOException e) {
            String message = "Can't close pipeline";
            throw new DalException(message, e);
        }
    }

    @Override
    void rollback() {
        super.rollback();

        if (oddsToUpdate != null) {
            oddsToUpdate.clear();
        }
    }
}
