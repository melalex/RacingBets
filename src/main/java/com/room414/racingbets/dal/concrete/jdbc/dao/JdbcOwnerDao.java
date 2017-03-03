package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.OwnerDao;
import com.room414.racingbets.dal.concrete.jdbc.base.JdbcPersonDao;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcMapHelper;
import com.room414.racingbets.dal.domain.entities.Owner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of OwnerDao that uses JDBC as data source.
 *
 * @see OwnerDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcOwnerDao extends JdbcPersonDao<Owner> implements OwnerDao {
    private static final String TABLE_NAME = "owner";

    JdbcOwnerDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Owner mapResultSet(ResultSet resultSet) throws SQLException {
        return JdbcMapHelper.mapOwner(resultSet);
    }
}
