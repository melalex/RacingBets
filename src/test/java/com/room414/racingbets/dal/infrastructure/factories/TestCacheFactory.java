package com.room414.racingbets.dal.infrastructure.factories;

import com.room414.racingbets.dal.abstraction.cache.*;
import com.room414.racingbets.dal.abstraction.infrastructure.Getter;
import com.room414.racingbets.dal.concrete.caching.caffeine.caches.*;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.MainCachePool;
import com.room414.racingbets.dal.concrete.caching.redis.RedisBetCache;
import com.room414.racingbets.dal.concrete.caching.redis.RedisCache;

import static org.mockito.Mockito.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 16 Mar 2017
 */
public class TestCacheFactory {
    private MainCachePool mainCachePool;
    private RedisCache redisCache;
    private RedisBetCache redisBetCache;

    private TestCacheFactory() {

    }

    public static TestCacheFactory create() {
        TestCacheFactory factory = new TestCacheFactory();

        factory.mainCachePool = new MainCachePool();
        factory.redisCache = mock(RedisCache.class);
        when(factory.redisCache.getCached(anyString(), anyString(), any(), any()))
                .then(invocationOnMock -> ((Getter) invocationOnMock.getArguments()[2]).call());
        when(factory.redisCache.getCachedCount(anyString(), anyString(), any()))
                .then(invocationOnMock -> ((Getter) invocationOnMock.getArguments()[2]).call());

        factory.redisBetCache = mock(RedisBetCache.class);
        when(factory.redisBetCache.getCached(anyString(), anyString(), any(), any()))
                .then(invocationOnMock -> ((Getter) invocationOnMock.getArguments()[2]).call());

        when(factory.redisBetCache.getCachedCount(anyString(), anyString(), any()))
                .then(invocationOnMock -> ((Getter) invocationOnMock.getArguments()[2]).call());
        when(factory.redisBetCache.getOdds(any(), any()))
                .then(invocationOnMock -> ((Getter) invocationOnMock.getArguments()[1]).call());

        return factory;
    }

    public MainCachePool getMainCachePool() {
        return mainCachePool;
    }

    public ApplicationUserCache createApplicationUserCache(BetCache betCache) {
            return new CaffeineApplicationUserCache(
                    mainCachePool.getApplicationUserCachePool().getCache(),
                    mainCachePool.getApplicationUserCachePool().getListCache(),
                    mainCachePool.getApplicationUserCachePool().getCountCache(),
                    redisCache,
                    betCache
            );
    }

    public BetCache createBetCache() {
        return new CaffeineBetCache(
                mainCachePool.getBetCachePool().getCache(),
                mainCachePool.getBetCachePool().getListCache(),
                mainCachePool.getBetCachePool().getCountCache(),
                mainCachePool.getOddsCachePool().getCache(),
                redisBetCache
        );
    }

    public HorseCache createHorseCache(ParticipantCache participantCache) {
        return new CaffeineHorseCache(
                mainCachePool.getHorseCachePool().getCache(),
                mainCachePool.getHorseCachePool().getListCache(),
                mainCachePool.getHorseCachePool().getCountCache(),
                redisCache,
                participantCache
        );
    }

    public JockeyCache createJockeyCache(ParticipantCache participantCache) {
        return new CaffeineJockeyCache(
                mainCachePool.getJockeyCachePool().getCache(),
                mainCachePool.getJockeyCachePool().getListCache(),
                mainCachePool.getJockeyCachePool().getCountCache(),
                redisCache,
                participantCache
        );
    }

    public OwnerCache createOwnerCache(HorseCache horseCache) {
        return new CaffeineOwnerCache(
                mainCachePool.getOwnerCachePool().getCache(),
                mainCachePool.getOwnerCachePool().getListCache(),
                mainCachePool.getOwnerCachePool().getCountCache(),
                redisCache,
                horseCache
        );
    }

    public ParticipantCache createParticipantCache(RaceCache raceCache) {
        return new CaffeineParticipantCache(
                mainCachePool.getParticipantCachePool().getCache(),
                mainCachePool.getParticipantCachePool().getListCache(),
                mainCachePool.getParticipantCachePool().getCountCache(),
                mainCachePool.getWhoAndWhenCachePool().getListCache(),
                redisCache,
                raceCache
        );
    }

    public RaceCache createRaceCache(ParticipantCache participantCache, BetCache betCache) {
        return new CaffeineRaceCache(
                mainCachePool.getRaceCachePool().getCache(),
                mainCachePool.getRaceCachePool().getListCache(),
                mainCachePool.getRaceCachePool().getCountCache(),
                participantCache,
                betCache,
                redisCache
        );
    }

    public RacecourseCache createRacecourseCache(RaceCache raceCache) {
        return new CaffeineRacecourseCache(
                mainCachePool.getRacecourseCachePool().getCache(),
                mainCachePool.getRacecourseCachePool().getListCache(),
                mainCachePool.getRacecourseCachePool().getCountCache(),
                redisCache,
                raceCache
        );
    }

    public TrainerCache createTrainerCache(HorseCache horseCache) {
        return new CaffeineTrainerCache(
                mainCachePool.getTrainerCachePool().getCache(),
                mainCachePool.getTrainerCachePool().getListCache(),
                mainCachePool.getTrainerCachePool().getCountCache(),
                redisCache,
                horseCache
        );
    }
}
