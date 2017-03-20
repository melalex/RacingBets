package com.room414.racingbets.bll.concrete.factories.services;

import com.room414.racingbets.bll.abstraction.factories.services.BetServiceFactory;
import com.room414.racingbets.bll.abstraction.services.BetService;
import com.room414.racingbets.bll.concrete.services.BetServiceImpl;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class BetServiceFactoryImpl implements BetServiceFactory {
    private UnitOfWorkFactory unitOfWorkFactory;

    BetServiceFactoryImpl(UnitOfWorkFactory unitOfWorkFactory) {
        this.unitOfWorkFactory = unitOfWorkFactory;
    }

    @Override
    public BetService create() {
        return new BetServiceImpl(unitOfWorkFactory);
    }
}
