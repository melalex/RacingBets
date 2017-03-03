package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.builders.PersonBuilder;
import com.room414.racingbets.dal.abstraction.dao.TrainerDao;
import com.room414.racingbets.dal.concrete.jdbc.base.JdbcPersonDao;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcMapHelper;
import com.room414.racingbets.dal.domain.entities.Country;
import com.room414.racingbets.dal.domain.entities.Trainer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of TrainerDao that uses JDBC as data source.
 *
 * @see TrainerDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcTrainerDao extends JdbcPersonDao<Trainer> implements TrainerDao {
    private static final String TABLE_NAME = "trainer";

    JdbcTrainerDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Trainer mapResultSet(ResultSet resultSet) throws SQLException {
        return JdbcMapHelper.mapTrainer(resultSet);
    }
}
