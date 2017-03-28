package com.room414.racingbets.dal.abstraction.dao;


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

    RaceDao getRaceDao();

    RacecourseDao getRacecourseDao();

    void commit();

    /**
     * this method is called while participating in a distributed transaction,
     * this method is called on a closed connection
     */
    void rollback();

    @Override
    void close();
}
