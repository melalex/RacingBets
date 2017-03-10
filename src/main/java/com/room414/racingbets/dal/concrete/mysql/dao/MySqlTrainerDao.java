package com.room414.racingbets.dal.concrete.mysql.dao;

import com.room414.racingbets.dal.abstraction.dao.TrainerDao;
import com.room414.racingbets.dal.concrete.mysql.base.MySqlPersonDao;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlMapHelper;
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
public class MySqlTrainerDao extends MySqlPersonDao<Trainer> implements TrainerDao {
    private static final String TABLE_NAME = "trainer";

    MySqlTrainerDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Trainer mapResultSet(ResultSet resultSet) throws SQLException {
        return MySqlMapHelper.mapTrainer(resultSet);
    }
}
