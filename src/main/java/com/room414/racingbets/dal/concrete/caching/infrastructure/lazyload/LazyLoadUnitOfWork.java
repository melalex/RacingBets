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
    public ApplicationUserDao getApplicationUserDao() {
        return getUnitOfWork().getApplicationUserDao();
    }

    @Override
    public BetDao getBetDao() {
        return getUnitOfWork().getBetDao();
    }

    @Override
    public HorseDao getHorseDao() {
        return getUnitOfWork().getHorseDao();
    }

    @Override
    public JockeyDao getJockeyDao() {
        return getUnitOfWork().getJockeyDao();
    }

    @Override
    public OwnerDao getOwnerDao() {
        return getUnitOfWork().getOwnerDao();
    }

    @Override
    public TrainerDao getTrainerDao() {
        return getUnitOfWork().getTrainerDao();
    }

    @Override
    public ParticipantDao getParticipantDao() {
        return getUnitOfWork().getParticipantDao();
    }

    @Override
    public RaceDao getRaceDao() {
        return getUnitOfWork().getRaceDao();
    }

    @Override
    public RacecourseDao getRacecourseDao() {
        return getUnitOfWork().getRacecourseDao();
    }

    @Override
    public void commit() {
        getUnitOfWork().commit();
    }

    @Override
    public void rollback() {
        getUnitOfWork().rollback();
    }

    @Override
    public void close() {
        getUnitOfWork().close();
    }
}
