package com.room414.racingbets.dal.concrete.mysql.factories;

import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;

/**
 * @author Alexander Melashchenko
 * @version 1.0 07 Mar 2017
 */
public class MySqlTestingUnitOfWorkFactory implements UnitOfWorkFactory {
    private static MySqlTestingUnitOfWorkFactory ourInstance = new MySqlTestingUnitOfWorkFactory();

    public static MySqlTestingUnitOfWorkFactory getInstance() {
        return ourInstance;
    }

    private MySqlTestingUnitOfWorkFactory() {
    }

    @Override
    public UnitOfWork create() throws DalException {
        return null;
    }
}
