package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.exception.DalException;

/**
 * Manages transactions to persistent storage.
 *
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public interface UnitOfWork extends AutoCloseable {
    ApplicationUserDao getApplicationUserDao() throws DalException;
    BetDao getBetDao() throws DalException;

    HorseDao getHorseDao() throws DalException;
    JockeyDao getJockeyDao() throws DalException;
    OwnerDao getOwnerDao() throws DalException;
    TrainerDao getTrainerDao() throws DalException;

    ParticipantDao getParticipantDao() throws DalException;
    RaceDao getRaceDao() throws DalException;

    RacecourseDao getRacecourseDao() throws DalException;

    void commit() throws DalException;
    void rollback() throws DalException;
}
