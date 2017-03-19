package com.room414.racingbets.dal.concrete.mysql.factories;

import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.mysql.dao.MySqlUnitOfWork;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Alexander Melashchenko
 * @version 1.0 02 Mar 2017
 */
public class MySqlUnitOfWorkFactory implements UnitOfWorkFactory {
    private Log log = LogFactory.getLog(MySqlUnitOfWorkFactory.class);

    private DataSource connectionPool;

    public MySqlUnitOfWorkFactory(DataSource connectionPool) {
    }

    @Override
    public UnitOfWork createUnitOfWork() {
        try {
            return new MySqlUnitOfWork(connectionPool.getConnection());
        } catch (SQLException e) {
            String message = "Can't get new connection from pool";
            log.error(message);
            throw new DalException(message, e);
        }
    }
}
