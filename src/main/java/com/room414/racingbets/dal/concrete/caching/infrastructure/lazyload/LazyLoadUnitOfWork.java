package com.room414.racingbets.dal.concrete.caching.infrastructure.lazyload;

import com.room414.racingbets.dal.abstraction.dao.*;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class LazyLoadUnitOfWork implements UnitOfWork {
    private UnitOfWorkFactory factory;
    private UnitOfWork unitOfWork;

    public LazyLoadUnitOfWork(UnitOfWorkFactory factory) {
        this.factory = factory;
    }

    private UnitOfWork getUnitOfWork() throws DalException {
        if (unitOfWork == null) {
            unitOfWork = factory.createUnitOfWork();
        }
        return unitOfWork;
    }

    @Override
    public ApplicationUserDao getApplicationUserDao() throws DalException {
        return getUnitOfWork().getApplicationUserDao();
    }

    @Override
    public BetDao getBetDao() throws DalException {
        return getUnitOfWork().getBetDao();
    }

    @Override
    public HorseDao getHorseDao() throws DalException {
        return getUnitOfWork().getHorseDao();
    }

    @Override
    public JockeyDao getJockeyDao() throws DalException {
        return getUnitOfWork().getJockeyDao();
    }

    @Override
    public OwnerDao getOwnerDao() throws DalException {
        return getUnitOfWork().getOwnerDao();
    }

    @Override
    public TrainerDao getTrainerDao() throws DalException {
        return getUnitOfWork().getTrainerDao();
    }

    @Override
    public ParticipantDao getParticipantDao() throws DalException {
        return getUnitOfWork().getParticipantDao();
    }

    @Override
    public RaceDao getRaceDao() throws DalException {
        return getUnitOfWork().getRaceDao();
    }

    @Override
    public RacecourseDao getRacecourseDao() throws DalException {
        return getUnitOfWork().getRacecourseDao();
    }

    @Override
    public void commit() throws DalException {
        getUnitOfWork().commit();
    }

    @Override
    public void rollback() throws DalException {
        getUnitOfWork().rollback();
    }

    @Override
    public void close() throws Exception {
        getUnitOfWork().close();
    }
}
