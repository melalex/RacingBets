package com.room414.racingbets.dal.concrete.mysql.dao;

import com.room414.racingbets.dal.abstraction.dao.*;
import com.room414.racingbets.dal.abstraction.exception.DalException;

import java.sql.Connection;

/**
 * @author Alexander Melashchenko
 * @version 1.0 07 Mar 2017
 */
public class MySqlTestingUnitOfWork implements UnitOfWork {
    private Connection connection;

    public MySqlTestingUnitOfWork(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ApplicationUserDao getApplicationUserDao() {
        return new MySqlApplicationUserDao(connection);
    }

    @Override
    public BetDao getBetDao() {
        return null;
    }

    @Override
    public HorseDao getHorseDao() {
        return null;
    }

    @Override
    public JockeyDao getJockeyDao() {
        return null;
    }

    @Override
    public OwnerDao getOwnerDao() {
        return null;
    }

    @Override
    public TrainerDao getTrainerDao() {
        return null;
    }

    @Override
    public ParticipantDao getParticipantDao() {
        return null;
    }

    @Override
    public RaceDao getRaceDao() {
        return null;
    }

    @Override
    public RacecourseDao getRacecourseDao() {
        return null;
    }

    @Override
    public void commit() throws DalException {
        throw new UnsupportedOperationException("MySqlTestingUnitOfWork сan not manage transactions");
    }

    @Override
    public void rollback() throws DalException {
        throw new UnsupportedOperationException("MySqlTestingUnitOfWork сan not manage transactions");
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
