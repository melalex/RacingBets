package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.JockeyDao;
import com.room414.racingbets.dal.concrete.jdbc.base.JdbcPersonDao;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcMapHelper;
import com.room414.racingbets.dal.domain.entities.Jockey;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of JockeyDao that uses JDBC as data source.
 *
 * @see JockeyDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcJockeyDao extends JdbcPersonDao<Jockey> implements JockeyDao {
    private static final String TABLE_NAME = "jockey";

    JdbcJockeyDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Jockey mapResultSet(ResultSet resultSet) throws SQLException {
        return JdbcMapHelper.mapJockey(resultSet);
    }
}
