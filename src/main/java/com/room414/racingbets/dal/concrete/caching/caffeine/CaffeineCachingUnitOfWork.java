package com.room414.racingbets.dal.concrete.caching.caffeine;

import com.room414.racingbets.dal.abstraction.cache.*;
import com.room414.racingbets.dal.concrete.caching.caffeine.caches.*;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.MainCachePool;
import com.room414.racingbets.dal.concrete.caching.redis.RedisUnitOfWork;

/**
 * @author Alexander Melashchenko
 * @version 1.0 15 Mar 2017
 */
public class CaffeineCachingUnitOfWork implements CachingUnitOfWork {
    private MainCachePool mainCachePool;
    private RedisUnitOfWork redisUnitOfWork;

    private ApplicationUserCache applicationUserCache;
    private BetCache betCache;
    private HorseCache horseCache;
    private JockeyCache jockeyCache;
    private OwnerCache ownerCache;
    private ParticipantCache participantCache;
    private RaceCache raceCache;
    private RacecourseCache racecourseCache;
    private TrainerCache trainerCache;

    public CaffeineCachingUnitOfWork(MainCachePool mainCachePool, RedisUnitOfWork redisUnitOfWork) {
        this.mainCachePool = mainCachePool;
        this.redisUnitOfWork = redisUnitOfWork;
    }

    @Override
    public ApplicationUserCache getApplicationUserCache() {
        if (applicationUserCache == null) {
            applicationUserCache = new CaffeineApplicationUserCache(
                    mainCachePool.getApplicationUserCachePool(),
                    redisUnitOfWork.getRedisCache(),
                    getBetCache()
            );
        }
        return applicationUserCache;
    }

    @Override
    public BetCache getBetCache() {
        if (betCache == null) {
            betCache = new CaffeineBetCache(
                    mainCachePool.getBetCachePool(),
                    mainCachePool.getOddsCachePool(),
                    redisUnitOfWork.getRedisBetCache()
            );
        }
        return betCache;
    }

    @Override
    public HorseCache getHorseCache() {
        if (horseCache == null) {
            horseCache = new CaffeineHorseCache(
                    mainCachePool.getHorseCachePool(),
                    getParticipantCache(),
                    redisUnitOfWork.getRedisCache()
            );
        }
        return horseCache;
    }

    @Override
    public JockeyCache getJockeyCache() {
        if (jockeyCache == null) {
            jockeyCache = new CaffeineJockeyCache(
                    mainCachePool.getJockeyCachePool(),
                    redisUnitOfWork.getRedisCache(),
                    getParticipantCache()
            );
        }
        return jockeyCache;
    }

    @Override
    public OwnerCache getOwnerCache() {
        if (ownerCache == null) {
            ownerCache = new CaffeineOwnerCache(
                    mainCachePool.getOwnerCachePool(),
                    redisUnitOfWork.getRedisCache(),
                    getHorseCache()
            );
        }
        return ownerCache;
    }

    @Override
    public ParticipantCache getParticipantCache() {
        if (participantCache == null) {
            participantCache = new CaffeineParticipantCache(
                    mainCachePool.getParticipantCachePool(),
                    redisUnitOfWork.getRedisCache(),
                    getRaceCache()
            );
        }
        return participantCache;
    }

    @Override
    public RaceCache getRaceCache() {
        if (raceCache == null) {
            raceCache = new CaffeineRaceCache(
                    mainCachePool.getRaceCachePool(),
                    redisUnitOfWork.getRedisCache(),
                    getBetCache(),
                    getParticipantCache()
            );
        }
        return raceCache;
    }

    @Override
    public RacecourseCache getRacecourseCache() {
        if (racecourseCache == null) {
            racecourseCache = new CaffeineRacecourseCache(
                    mainCachePool.getRacecourseCachePool(),
                    redisUnitOfWork.getRedisCache(),
                    getRaceCache()
            );
        }
        return racecourseCache;
    }

    @Override
    public TrainerCache getTrainerCache() {
        if (trainerCache == null) {
            trainerCache = new CaffeineTrainerCache(
                    mainCachePool.getTrainerCachePool(),
                    redisUnitOfWork.getRedisCache(),
                    getHorseCache()
            );
        }
        return trainerCache;
    }

    @Override
    public void commit() {
        redisUnitOfWork.commit();
    }

    @Override
    public void rollback() {
        redisUnitOfWork.rollback();
    }

    @Override
    public void close() {
        redisUnitOfWork.close();
    }
}
