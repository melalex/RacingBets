package com.room414.racingbets.dal.concrete.caching.factories;

import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.caching.dao.CacheUnitOfWork;
import com.room414.racingbets.dal.concrete.caching.proxies.LazyLoadUnitOfWork;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CacheUnitOfWorkFactory implements UnitOfWorkFactory {
    private UnitOfWorkFactory factory;

    public CacheUnitOfWorkFactory(UnitOfWorkFactory factory) {
        this.factory = factory;
    }

    @Override
    public UnitOfWork createUnitOfWork() throws DalException {
        return new CacheUnitOfWork(new LazyLoadUnitOfWork(factory));
    }
}
