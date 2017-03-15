package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.exception.DalException;

/**
 * Manages transactions to persistent storage.
 *
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public interface UnitOfWork extends AutoCloseable {
    ApplicationUserDao getApplicationUserDao();
    BetDao getBetDao();

    HorseDao getHorseDao();
    JockeyDao getJockeyDao();
    OwnerDao getOwnerDao();
    TrainerDao getTrainerDao();

    ParticipantDao getParticipantDao();
    RaceDao getRaceDao();

    RacecourseDao getRacecourseDao();

    void commit();
    void rollback();
}
