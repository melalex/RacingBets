package com.room414.racingbets.dal.concrete.caching.factories;

import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.caching.caffeine.CachingUnitOfWork;
import com.room414.racingbets.dal.concrete.caching.dao.CachedUnitOfWork;
import com.room414.racingbets.dal.concrete.caching.infrastructure.lazyload.LazyLoadUnitOfWork;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CachedUnitOfWorkFactory implements UnitOfWorkFactory {
    private UnitOfWorkFactory unitOfWorkFactory;
    private CachingUnitOfWorkFactory cachingUnitOfWorkFactory;

    public CachedUnitOfWorkFactory(UnitOfWorkFactory unitOfWorkFactory, CachingUnitOfWorkFactory cachingUnitOfWorkFactory) {
        this.unitOfWorkFactory = unitOfWorkFactory;
        this.cachingUnitOfWorkFactory = cachingUnitOfWorkFactory;
    }

    @Override
    public UnitOfWork createUnitOfWork() throws DalException {
        UnitOfWork unitOfWork = new LazyLoadUnitOfWork(unitOfWorkFactory);
        CachingUnitOfWork cachingUnitOfWork = cachingUnitOfWorkFactory.create();
        return new CachedUnitOfWork(unitOfWork, cachingUnitOfWork);
    }
}
