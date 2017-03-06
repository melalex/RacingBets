package com.room414.racingbets.dal.concrete.mysql.dao;

import com.room414.racingbets.dal.abstraction.dao.JockeyDao;
import com.room414.racingbets.dal.concrete.mysql.base.MySqlPersonDao;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlMapHelper;
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
public class MySqlJockeyDao extends MySqlPersonDao<Jockey> implements JockeyDao {
    private static final String TABLE_NAME = "jockey";

    MySqlJockeyDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Jockey mapResultSet(ResultSet resultSet) throws SQLException {
        return MySqlMapHelper.mapJockey(resultSet);
    }
}
