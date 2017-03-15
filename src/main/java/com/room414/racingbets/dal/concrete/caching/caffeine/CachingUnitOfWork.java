package com.room414.racingbets.dal.concrete.caching.caffeine;

import com.room414.racingbets.dal.concrete.caching.caffeine.caches.*;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.MainCachePool;
import com.room414.racingbets.dal.concrete.caching.redis.RedisUnitOfWork;

/**
 * @author Alexander Melashchenko
 * @version 1.0 15 Mar 2017
 */
public class CachingUnitOfWork implements AutoCloseable {
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

    public CachingUnitOfWork(MainCachePool mainCachePool, RedisUnitOfWork redisUnitOfWork) {
        this.mainCachePool = mainCachePool;
        this.redisUnitOfWork = redisUnitOfWork;
    }

    public ApplicationUserCache getApplicationUserCache() {
        if (applicationUserCache == null) {
            applicationUserCache = new ApplicationUserCache(
                    mainCachePool.getApplicationUserCachePool().getCache(),
                    mainCachePool.getApplicationUserCachePool().getListCache(),
                    mainCachePool.getApplicationUserCachePool().getCountCache(),
                    redisUnitOfWork.getRedisCache(),
                    getBetCache()
            );
        }
        return applicationUserCache;
    }

    public BetCache getBetCache() {
        if (betCache == null) {
            betCache = new BetCache(
                    mainCachePool.getBetCachePool().getCache(),
                    mainCachePool.getBetCachePool().getListCache(),
                    mainCachePool.getBetCachePool().getCountCache(),
                    mainCachePool.getOddsCachePool().getCache(),
                    redisUnitOfWork.getRedisBetCache()
            );
        }
        return betCache;
    }

    public HorseCache getHorseCache() {
        if (horseCache == null) {
            horseCache = new HorseCache(
                    mainCachePool.getHorseCachePool().getCache(),
                    mainCachePool.getHorseCachePool().getListCache(),
                    mainCachePool.getHorseCachePool().getCountCache(),
                    redisUnitOfWork.getRedisBetCache(),
                    getParticipantCache()
            );
        }
        return horseCache;
    }

    public JockeyCache getJockeyCache() {
        if (jockeyCache == null) {
            jockeyCache = new JockeyCache(
                    mainCachePool.getJockeyCachePool().getCache(),
                    mainCachePool.getJockeyCachePool().getListCache(),
                    mainCachePool.getJockeyCachePool().getCountCache(),
                    redisUnitOfWork.getRedisBetCache(),
                    getParticipantCache()
            );
        }
        return jockeyCache;
    }

    public OwnerCache getOwnerCache() {
        if (ownerCache == null) {
            ownerCache = new OwnerCache(
                    mainCachePool.getOwnerCachePool().getCache(),
                    mainCachePool.getOwnerCachePool().getListCache(),
                    mainCachePool.getOwnerCachePool().getCountCache(),
                    redisUnitOfWork.getRedisBetCache(),
                    getHorseCache()
            );
        }
        return ownerCache;
    }

    public ParticipantCache getParticipantCache() {
        if (participantCache == null) {
            participantCache = new ParticipantCache(
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

    public RaceCache getRaceCache() {
        if (raceCache == null) {
            raceCache = new RaceCache(
                    mainCachePool.getRaceCachePool().getCache(),
                    mainCachePool.getRaceCachePool().getListCache(),
                    mainCachePool.getRaceCachePool().getCountCache(),
                    getParticipantCache(),
                    redisUnitOfWork.getRedisBetCache()
            );
        }
        return raceCache;
    }

    public RacecourseCache getRacecourseCache() {
        if (racecourseCache == null) {
            racecourseCache = new RacecourseCache(
                    mainCachePool.getRacecourseCachePool().getCache(),
                    mainCachePool.getRacecourseCachePool().getListCache(),
                    mainCachePool.getRacecourseCachePool().getCountCache(),
                    redisUnitOfWork.getRedisBetCache(),
                    getRaceCache()
            );
        }
        return racecourseCache;
    }

    public TrainerCache getTrainerCache() {
        if (trainerCache == null) {
            trainerCache = new TrainerCache(
                    mainCachePool.getTrainerCachePool().getCache(),
                    mainCachePool.getTrainerCachePool().getListCache(),
                    mainCachePool.getTrainerCachePool().getCountCache(),
                    redisUnitOfWork.getRedisBetCache(),
                    getHorseCache()
            );
        }
        return trainerCache;
    }

    public void commit() {
        redisUnitOfWork.commit();
    }

    public void rollback() {
        redisUnitOfWork.rollback();
    }

    @Override
    public void close() throws Exception {
        redisUnitOfWork.close();
    }
}
