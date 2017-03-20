package com.room414.racingbets.bll.concrete.factories.services;

import com.room414.racingbets.bll.abstraction.factories.services.JockeyServiceFactory;
import com.room414.racingbets.bll.abstraction.services.JockeyService;
import com.room414.racingbets.bll.concrete.services.JockeyServiceImpl;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class JockeyServiceFactoryImpl implements JockeyServiceFactory {
    private UnitOfWorkFactory unitOfWorkFactory;

    JockeyServiceFactoryImpl(UnitOfWorkFactory unitOfWorkFactory) {
        this.unitOfWorkFactory = unitOfWorkFactory;
    }

    @Override
    public JockeyService create() {
        return new JockeyServiceImpl(unitOfWorkFactory);
    }
}
