package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.JockeyDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.jdbc.base.JdbcPersonDao;
import com.room414.racingbets.dal.domain.entities.Jockey;

import java.sql.Connection;
import java.util.List;

/**
 * Implementation of JockeyDao that uses JDBC as data source.
 *
 * @see JockeyDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcJockeyDao extends JdbcPersonDao<Jockey> implements JockeyDao {
    private Connection connection;

    JdbcJockeyDao(Connection connection) {
        this.connection = connection;
    }


}
