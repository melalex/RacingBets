package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.dao.*;
import com.room414.racingbets.dal.abstraction.exception.DalException;

/**
 * @author melalex
 * @version 1.0 12 Mar 2017
 */
public class CacheUnitOfWork implements UnitOfWork {
    private UnitOfWork unitOfWork;

    public CacheUnitOfWork(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public ApplicationUserDao getApplicationUserDao() throws DalException {
        return unitOfWork.getApplicationUserDao();
    }

    @Override
    public BetDao getBetDao() throws DalException {
        return unitOfWork.getBetDao();
    }

    @Override
    public HorseDao getHorseDao() throws DalException {
        return unitOfWork.getHorseDao();
    }

    @Override
    public JockeyDao getJockeyDao() throws DalException {
        return unitOfWork.getJockeyDao();
    }

    @Override
    public OwnerDao getOwnerDao() throws DalException {
        return unitOfWork.getOwnerDao();
    }

    @Override
    public TrainerDao getTrainerDao() throws DalException {
        return unitOfWork.getTrainerDao();
    }

    @Override
    public ParticipantDao getParticipantDao() throws DalException {
        return unitOfWork.getParticipantDao();
    }

    @Override
    public RaceDao getRaceDao() throws DalException {
        return unitOfWork.getRaceDao();
    }

    @Override
    public RacecourseDao getRacecourseDao() throws DalException {
        return unitOfWork.getRacecourseDao();
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
