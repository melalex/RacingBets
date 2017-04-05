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
    private DataSource connectionPool;

    public MySqlUnitOfWorkFactory(DataSource connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public UnitOfWork createUnitOfWork() {
        try {
            return MySqlUnitOfWork.create(connectionPool.getConnection());
        } catch (SQLException e) {
            String message = "Can't get new connection from pool";
            throw new DalException(message, e);
        }
    }
}
