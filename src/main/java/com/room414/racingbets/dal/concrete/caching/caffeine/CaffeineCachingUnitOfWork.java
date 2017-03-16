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
                    mainCachePool.getApplicationUserCachePool().getCache(),
                    mainCachePool.getApplicationUserCachePool().getListCache(),
                    mainCachePool.getApplicationUserCachePool().getCountCache(),
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
                    mainCachePool.getBetCachePool().getCache(),
                    mainCachePool.getBetCachePool().getListCache(),
                    mainCachePool.getBetCachePool().getCountCache(),
                    mainCachePool.getOddsCachePool().getCache(),
                    redisUnitOfWork.getRedisBetCache()
            );
        }
        return betCache;
    }

    @Override
    public HorseCache getHorseCache() {
        if (horseCache == null) {
            horseCache = new CaffeineHorseCache(
                    mainCachePool.getHorseCachePool().getCache(),
                    mainCachePool.getHorseCachePool().getListCache(),
                    mainCachePool.getHorseCachePool().getCountCache(),
                    redisUnitOfWork.getRedisBetCache(),
                    getParticipantCache()
            );
        }
        return horseCache;
    }

    @Override
    public JockeyCache getJockeyCache() {
        if (jockeyCache == null) {
            jockeyCache = new CaffeineJockeyCache(
                    mainCachePool.getJockeyCachePool().getCache(),
                    mainCachePool.getJockeyCachePool().getListCache(),
                    mainCachePool.getJockeyCachePool().getCountCache(),
                    redisUnitOfWork.getRedisBetCache(),
                    getParticipantCache()
            );
        }
        return jockeyCache;
    }

    @Override
    public OwnerCache getOwnerCache() {
        if (ownerCache == null) {
            ownerCache = new CaffeineOwnerCache(
                    mainCachePool.getOwnerCachePool().getCache(),
                    mainCachePool.getOwnerCachePool().getListCache(),
                    mainCachePool.getOwnerCachePool().getCountCache(),
                    redisUnitOfWork.getRedisBetCache(),
                    getHorseCache()
            );
        }
        return ownerCache;
    }

    @Override
    public ParticipantCache getParticipantCache() {
        if (participantCache == null) {
            participantCache = new CaffeineParticipantCache(
                    mainCachePool.getParticipantCachePool().getCache(),
                    mainCachePool.getParticipantCachePool().getListCache(),
                    mainCachePool.getParticipantCachePool().getCountCache(),
                    mainCachePool.getWhoAndWhenCachePool().getListCache(),
                    redisUnitOfWork.getRedisBetCache(),
                    getRaceCache()
            );
        }
        return participantCache;
    }

    @Override
    public RaceCache getRaceCache() {
        if (raceCache == null) {
            raceCache = new CaffeineRaceCache(
                    mainCachePool.getRaceCachePool().getCache(),
                    mainCachePool.getRaceCachePool().getListCache(),
                    mainCachePool.getRaceCachePool().getCountCache(),
                    getParticipantCache(),
                    getBetCache(),
                    redisUnitOfWork.getRedisBetCache()
            );
        }
        return raceCache;
    }

    @Override
    public RacecourseCache getRacecourseCache() {
        if (racecourseCache == null) {
            racecourseCache = new CaffeineRacecourseCache(
                    mainCachePool.getRacecourseCachePool().getCache(),
                    mainCachePool.getRacecourseCachePool().getListCache(),
                    mainCachePool.getRacecourseCachePool().getCountCache(),
                    redisUnitOfWork.getRedisBetCache(),
                    getRaceCache()
            );
        }
        return racecourseCache;
    }

    @Override
    public TrainerCache getTrainerCache() {
        if (trainerCache == null) {
            trainerCache = new CaffeineTrainerCache(
                    mainCachePool.getTrainerCachePool().getCache(),
                    mainCachePool.getTrainerCachePool().getListCache(),
                    mainCachePool.getTrainerCachePool().getCountCache(),
                    redisUnitOfWork.getRedisBetCache(),
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
    public void close() throws Exception {
        redisUnitOfWork.close();
    }
}
