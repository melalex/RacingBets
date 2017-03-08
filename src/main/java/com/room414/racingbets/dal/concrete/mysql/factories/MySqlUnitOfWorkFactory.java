package com.room414.racingbets.dal.concrete.mysql.factories;

import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.mysql.dao.MySqlUnitOfWork;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Alexander Melashchenko
 * @version 1.0 02 Mar 2017
 */
public class MySqlUnitOfWorkFactory implements UnitOfWorkFactory {
    private static MySqlUnitOfWorkFactory ourInstance = new MySqlUnitOfWorkFactory();

    // TODO: add connection pool initialization
    private DataSource connectionPool;

    public static MySqlUnitOfWorkFactory getInstance() {
        return ourInstance;
    }

    private MySqlUnitOfWorkFactory() {
    }

    @Override
    public UnitOfWork create() throws DalException {
        try {
            return new MySqlUnitOfWork(connectionPool.getConnection());
        } catch (SQLException e) {
            throw new DalException("Can not get new connection from pool", e);
        }
    }
}