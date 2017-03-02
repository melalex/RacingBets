package com.room414.racingbets.dal.concrete.jdbc.factories;

import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.jdbc.dao.JdbcUnitOfWork;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Alexander Melashchenko
 * @version 1.0 02 Mar 2017
 */
public class JdbcUnitOfWorkFactory implements UnitOfWorkFactory {
    private static JdbcUnitOfWorkFactory ourInstance = new JdbcUnitOfWorkFactory();

    // TODO: add connection pool initialization
    private DataSource connectionPool;

    public static JdbcUnitOfWorkFactory getInstance() {
        return ourInstance;
    }

    private JdbcUnitOfWorkFactory() {
    }

    @Override
    public UnitOfWork create() throws DalException {
        try {
            return new JdbcUnitOfWork(connectionPool.getConnection());
        } catch (SQLException e) {
            throw new DalException("Can not get new connection from pool", e);
        }
    }
}
