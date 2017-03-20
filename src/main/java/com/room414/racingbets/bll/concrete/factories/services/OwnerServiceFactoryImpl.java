package com.room414.racingbets.bll.concrete.factories.services;

import com.room414.racingbets.bll.abstraction.factories.services.OwnerServiceFactory;
import com.room414.racingbets.bll.abstraction.services.OwnerService;
import com.room414.racingbets.bll.concrete.services.OwnerServiceImpl;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class OwnerServiceFactoryImpl implements OwnerServiceFactory {
    private UnitOfWorkFactory unitOfWorkFactory;

    OwnerServiceFactoryImpl(UnitOfWorkFactory unitOfWorkFactory) {
        this.unitOfWorkFactory = unitOfWorkFactory;
    }

    @Override
    public OwnerService create() {
        return new OwnerServiceImpl(unitOfWorkFactory);
    }
}
