package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.TrainerDao;
import com.room414.racingbets.dal.concrete.jdbc.base.JdbcPersonDao;
import com.room414.racingbets.dal.domain.entities.Trainer;

import java.sql.Connection;

/**
 * Implementation of TrainerDao that uses JDBC as data source.
 *
 * @see TrainerDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcTrainerDao extends JdbcPersonDao<Trainer> implements TrainerDao {
    Connection connection;

    JdbcTrainerDao(Connection connection) {
        this.connection = connection;
    }

}
