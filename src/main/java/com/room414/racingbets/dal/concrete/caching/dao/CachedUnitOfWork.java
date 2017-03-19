package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.cache.CachingUnitOfWork;
import com.room414.racingbets.dal.abstraction.dao.*;
import com.room414.racingbets.dal.concrete.caching.caffeine.CaffeineCachingUnitOfWork;
import com.room414.racingbets.dal.concrete.caching.infrastructure.lazyload.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CachedUnitOfWork implements UnitOfWork {
    private UnitOfWork unitOfWork;
    private CachingUnitOfWork cache;

    private ApplicationUserDao applicationUserDao;
    private BetDao betDao;

    private HorseDao horseDao;
    private JockeyDao jockeyDao;
    private OwnerDao ownerDao;
    private TrainerDao trainerDao;

    private ParticipantDao participantDao;
    private RaceDao raceDao;

    private RacecourseDao racecourseDao;


    public CachedUnitOfWork(UnitOfWork unitOfWork, CachingUnitOfWork cache) {
        this.unitOfWork = unitOfWork;
        this.cache = cache;
    }

    @Override
    public ApplicationUserDao getApplicationUserDao() {
        if (applicationUserDao == null) {
            applicationUserDao = new CachedApplicationUserDao(
                    new LazyLoadApplicationUserDao(unitOfWork),
                    cache.getApplicationUserCache()
            );
        }
        return applicationUserDao;
    }

    @Override
    public BetDao getBetDao() {
        if (betDao == null) {
            betDao = new CachedBetDao(
                    new LazyLoadBetDao(unitOfWork),
                    cache.getBetCache()
            );
        }
        return betDao;
    }

    @Override
    public HorseDao getHorseDao() {
        if (horseDao == null) {
            horseDao = new CachedHorseDao(
                    new LazyLoadHorseDao(unitOfWork),
                    cache.getHorseCache()
            );
        }
        return horseDao;
    }

    @Override
    public JockeyDao getJockeyDao() {
        if (jockeyDao == null) {
            jockeyDao = new CachedJockeyDao(
                    new LazyLoadJockeyDao(unitOfWork),
                    cache.getJockeyCache()
            );
        }
        return jockeyDao;
    }

    @Override
    public OwnerDao getOwnerDao() {
        if (ownerDao == null) {
            ownerDao = new CachedOwnerDao(
                    new LazyLoadOwnerDao(unitOfWork),
                    cache.getOwnerCache()
            );
        }
        return ownerDao;
    }

    @Override
    public TrainerDao getTrainerDao() {
        if (trainerDao == null) {
            trainerDao = new CachedTrainerDao(
                    new LazyLoadTrainerDao(unitOfWork),
                    cache.getTrainerCache()
            );
        }
        return trainerDao;
    }

    @Override
    public ParticipantDao getParticipantDao() {
        if (participantDao == null) {
            participantDao = new CachedParticipantDao(
                    new LazyLoadParticipantDao(unitOfWork),
                    cache.getParticipantCache()
            );
        }
        return participantDao;
    }

    @Override
    public RaceDao getRaceDao() {
        if (raceDao == null) {
            raceDao = new CachedRaceDao(
                    new LazyLoadRaceDao(unitOfWork),
                    cache.getRaceCache()
            );
        }
        return raceDao;
    }

    @Override
    public RacecourseDao getRacecourseDao() {
        if (racecourseDao == null) {
            racecourseDao = new CachedRacecourseDao(
                    new LazyLoadRacecourseDao(unitOfWork),
                    cache.getRacecourseCache()
            );
        }
        return racecourseDao;
    }

    @Override
    public void commit() {
        unitOfWork.commit();
        cache.commit();
    }

    @Override
    public void rollback() {
        unitOfWork.rollback();
        cache.rollback();
    }

    @Override
    public void close() {
        unitOfWork.close();
        cache.close();
    }
}
