package com.room414.racingbets.dal.concrete.mysql.dao;

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
public class MySqlUnitOfWork implements UnitOfWork {
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

    public MySqlUnitOfWork(Connection connection) {
        this.connection = connection;
    }

    public static MySqlUnitOfWork create(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        return new MySqlUnitOfWork(connection);
    }

    @Override
    public ApplicationUserDao getApplicationUserDao() {
        if (applicationUserDao == null) {
            applicationUserDao = new MySqlApplicationUserDao(connection);
        }
        return applicationUserDao;
    }

    @Override
    public BetDao getBetDao() {
        if (betDao == null) {
            betDao = new MySqlBetDao(connection);
        }
        return betDao;
    }

    @Override
    public HorseDao getHorseDao() {
        if (horseDao == null) {
            horseDao = new MySqlHorseDao(connection);
        }
        return horseDao;
    }

    @Override
    public JockeyDao getJockeyDao() {
        if (jockeyDao == null) {
            jockeyDao = new MySqlJockeyDao(connection);
        }
        return jockeyDao;
    }

    @Override
    public OwnerDao getOwnerDao() {
        if (ownerDao == null) {
            ownerDao = new MySqlOwnerDao(connection);
        }
        return ownerDao;
    }

    @Override
    public TrainerDao getTrainerDao() {
        if (trainerDao == null) {
            trainerDao = new MySqlTrainerDao(connection);
        }
        return trainerDao;
    }

    @Override
    public ParticipantDao getParticipantDao() {
        if (participantDao == null) {
            participantDao = new MySqlParticipantDao(connection);
        }
        return participantDao;
    }

    @Override
    public RaceDao getRaceDao() {
        if (raceDao == null) {
            raceDao = new MySqlRaceDao(connection);
        }
        return raceDao;
    }

    @Override
    public RacecourseDao getRacecourseDao() {
        if (racecourseDao == null) {
            racecourseDao = new MySqlRacecourseDao(connection);
        }
        return racecourseDao;
    }

    @Override
    public void commit() throws DalException {
        try {
            connection.commit();
        } catch (SQLException e) {
            String message = "Exception during committing transaction";
            throw new DalException(message, e);
        }
    }

    @Override
    public void rollback() throws DalException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            String message = "Exception during the rollback transaction";
            throw new DalException(message, e);
        }
    }

    @Override
    public void close() throws DalException {
        try {
            connection.close();
        } catch (SQLException e) {
            String message = "Exception during clothing connection";
            throw new DalException(message, e);
        }
    }
}
