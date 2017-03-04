package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.*;
import com.room414.racingbets.dal.abstraction.exception.DalException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation of UnitOfWork that uses JDBC as data source.
 *
 * @see UnitOfWork
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcUnitOfWork implements UnitOfWork {
    private Connection connection;

    private ApplicationUserDao applicationUserDao;
    private BetDao betDao;

    private HorseDao horseDao;
    private JockeyDao jockeyDao;
    private OwnerDao ownerDao;
    private TrainerDao trainerDao;

    private ParticipantDao participantDao;
    private RaceDao raceDao;

    private RacecourseDao racecourseDao;

    public JdbcUnitOfWork(Connection connection) {
        this.connection = connection;
    }

    public static JdbcUnitOfWork create(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        return new JdbcUnitOfWork(connection);
    }

    @Override
    public ApplicationUserDao getApplicationUserDao() {
        if (applicationUserDao == null) {
            applicationUserDao = new JdbcApplicationUserDao(connection);
        }
        return applicationUserDao;
    }

    @Override
    public BetDao getBetDao() {
        if (betDao == null) {
            betDao = new JdbcBetDao(connection);
        }
        return betDao;
    }

    @Override
    public HorseDao getHorseDao() {
        if (horseDao == null) {
            horseDao = new JdbcHorseDao(connection);
        }
        return horseDao;
    }

    @Override
    public JockeyDao getJockeyDao() {
        if (jockeyDao == null) {
            jockeyDao = new JdbcJockeyDao(connection);
        }
        return jockeyDao;
    }

    @Override
    public OwnerDao getOwnerDao() {
        if (ownerDao == null) {
            ownerDao = new JdbcOwnerDao(connection);
        }
        return ownerDao;
    }

    @Override
    public TrainerDao getTrainerDao() {
        if (trainerDao == null) {
            trainerDao = new JdbcTrainerDao(connection);
        }
        return trainerDao;
    }

    @Override
    public ParticipantDao getParticipantDao() {
        if (participantDao == null) {
            participantDao = new JdbcParticipantDao(connection);
        }
        return participantDao;
    }

    @Override
    public RaceDao getRaceDao() {
        if (raceDao == null) {
            raceDao = new JdbcRaceDao(connection);
        }
        return raceDao;
    }

    @Override
    public RacecourseDao getRacecourseDao() {
        if (racecourseDao == null) {
            racecourseDao = new JdbcRacecourseDao(connection);
        }
        return racecourseDao;
    }

    @Override
    public void commit() throws DalException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new DalException("Exception during committing transaction", e);
        }
    }

    @Override
    public void rollback() throws DalException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DalException("Exception during the rollback transaction", e);
        }
    }

    @Override
    public void close() throws DalException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DalException("Exception during clothing connection", e);
        }
    }
}
