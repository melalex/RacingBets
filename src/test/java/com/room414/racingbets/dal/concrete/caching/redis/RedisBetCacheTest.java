package com.room414.racingbets.dal.concrete.caching.redis;

import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;
import com.room414.racingbets.dal.domain.enums.BetType;
import com.room414.racingbets.dal.infrastructure.factories.TestingRedisFactory;
import com.room414.racingbets.dal.resolvers.RedisParameterResolver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.math.BigDecimal;

import static com.room414.racingbets.dal.infrastructure.TestHelper.defaultAssertionFailMessage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 16 Mar 2017
 */
@ExtendWith(RedisParameterResolver.class)
class RedisBetCacheTest {
    private static TestingRedisFactory factory;
    private RedisUnitOfWork unitOfWork;
    private RedisBetCache cache;

    @BeforeAll
    static void setUp(TestingRedisFactory factory) {
        RedisBetCacheTest.factory = factory;
    }

    @AfterAll
    static void tearDown() throws Exception {
        try(Jedis jedis = factory.getConnection()) {
            jedis.flushDB();
        }
        factory.close();
    }

    @BeforeEach
    void beforeEach() {
        unitOfWork = factory.create();
        cache = unitOfWork.getRedisBetCache();
    }

    @AfterEach
    void afterEach() throws Exception {
        unitOfWork.close();
    }

    @Test
    void getOdds() {
        Bet target = Bet.builder()
                .setBetSize(BigDecimal.valueOf(100))
                .setBetType(BetType.WIN)
                .setParticipantById(1, 1)
                .setRaceId(System.currentTimeMillis())
                .build();

        Odds expectedResult = new Odds(200, 100, 0.14);
        Odds result = cache.getOdds(target, () -> expectedResult);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);

        result = cache.getOdds(target, () -> null);

        assert expectedResult.equals(result) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    void updateOdds() throws IOException {
        Bet target = Bet.builder()
                .setBetSize(BigDecimal.valueOf(100))
                .setBetType(BetType.WIN)
                .setParticipantById(1, 1)
                .setRaceId(System.currentTimeMillis())
                .build();

        Odds odds = new Odds(200, 100, 0.14);
        Odds result = cache.getOdds(target, () -> odds);

        assert result.equals(odds) : defaultAssertionFailMessage(result, odds);

        Odds expectedResult = new Odds(300, 200, 0.14);

        cache.updateOdds(target);
        cache.commit();

        result = cache.getOdds(target, () -> null);

        assert expectedResult.equals(result) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    void deleteOdds() throws IOException {
        long raceId = System.currentTimeMillis();

        Bet target = Bet.builder()
                .setBetSize(BigDecimal.valueOf(100))
                .setBetType(BetType.WIN)
                .setParticipantById(1, 1)
                .setRaceId(raceId)
                .build();

        Odds odds = new Odds(200, 100, 0.14);
        Odds result = cache.getOdds(target, () -> odds);

        assert result.equals(odds) : defaultAssertionFailMessage(result, odds);

        cache.deleteOdds(raceId);
        cache.commit();

        Odds mock = new Odds(0, 0, 0);
        result = cache.getOdds(target, () -> mock);

        assert result.equals(mock) : defaultAssertionFailMessage(result, mock);
    }

    @Test
    void getOdds_getterReturnNull_IllegalArgumentExceptionThrown() {
        Bet mock = Bet.builder()
                .setBetSize(BigDecimal.valueOf(100))
                .setBetType(BetType.WIN)
                .setParticipantById(1, 1)
                .setRaceId(System.currentTimeMillis())
                .build();

        assertThrows(IllegalArgumentException.class, () -> cache.getOdds(mock, () -> null));
    }
}