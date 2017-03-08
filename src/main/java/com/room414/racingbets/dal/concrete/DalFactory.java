package com.room414.racingbets.dal.concrete;

import com.room414.racingbets.dal.abstraction.factories.AbstractDalFactory;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.mysql.factories.MySqlUnitOfWorkFactory;

/**
 * @author melalex
 * @version 1.0 08 Mar 2017
 */
public class DalFactory implements AbstractDalFactory {
    private static DalFactory ourInstance = new DalFactory();

    private UnitOfWorkFactory factory = new MySqlUnitOfWorkFactory();

    public static DalFactory getInstance() {
        return ourInstance;
    }

    private DalFactory() {
    }

    @Override
    public UnitOfWorkFactory createUnitOfWorkFactory() {
        return factory;
    }
}
