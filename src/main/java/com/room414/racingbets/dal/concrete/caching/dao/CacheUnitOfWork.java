package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.dao.*;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.caching.lazyload.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CacheUnitOfWork implements UnitOfWork {
    private UnitOfWork unitOfWork;

    private ApplicationUserDao applicationUserDao;
    private BetDao betDao;

    private HorseDao horseDao;
    private JockeyDao jockeyDao;
    private OwnerDao ownerDao;
    private TrainerDao trainerDao;

    private ParticipantDao participantDao;
    private RaceDao raceDao;

    private RacecourseDao racecourseDao;


    public CacheUnitOfWork(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public ApplicationUserDao getApplicationUserDao() throws DalException {
        if (applicationUserDao == null) {
            applicationUserDao = new CacheApplicationUserDao(new LazyLoadApplicationUserDao(unitOfWork));
        }
        return applicationUserDao;
    }

    @Override
    public BetDao getBetDao() throws DalException {
        if (betDao == null) {
            betDao = new CacheBetDao(new LazyLoadBetDao(unitOfWork));
        }
        return betDao;
    }

    @Override
    public HorseDao getHorseDao() throws DalException {
        if (horseDao == null) {
            horseDao = new CacheHorseDao(new LazyLoadHorseDao(unitOfWork));
        }
        return horseDao;
    }

    @Override
    public JockeyDao getJockeyDao() throws DalException {
        if (jockeyDao == null) {
            jockeyDao = new CacheJockeyDao(new LazyLoadJockeyDao(unitOfWork));
        }
        return jockeyDao;
    }

    @Override
    public OwnerDao getOwnerDao() throws DalException {
        if (ownerDao == null) {
            ownerDao = new CacheOwnerDao(new LazyLoadOwnerDao(unitOfWork));
        }
        return ownerDao;
    }

    @Override
    public TrainerDao getTrainerDao() throws DalException {
        if (trainerDao == null) {
            trainerDao = new CacheTrainerDao(new LazyLoadTrainerDao(unitOfWork));
        }
        return trainerDao;
    }

    @Override
    public ParticipantDao getParticipantDao() throws DalException {
        if (participantDao == null) {
            participantDao = new CacheParticipantDao(new LazyLoadParticipantDao(unitOfWork));
        }
        return participantDao;
    }

    @Override
    public RaceDao getRaceDao() throws DalException {
        if (raceDao == null) {
            raceDao = new CacheRaceDao(new LazyLoadRaceDao(unitOfWork));
        }
        return raceDao;
    }

    @Override
    public RacecourseDao getRacecourseDao() throws DalException {
        if (racecourseDao == null) {
            racecourseDao = new CacheRacecourseDao(new LazyLoadRacecourseDao(unitOfWork));
        }
        return racecourseDao;
    }

    @Override
    public void commit() throws DalException {
        unitOfWork.commit();
    }

    @Override
    public void rollback() throws DalException {
        unitOfWork.rollback();
    }

    @Override
    public void close() throws Exception {
        unitOfWork.close();
    }
}
