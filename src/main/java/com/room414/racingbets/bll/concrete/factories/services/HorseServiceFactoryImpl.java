package com.room414.racingbets.bll.concrete.factories.services;

import com.room414.racingbets.bll.abstraction.factories.services.HorseServiceFactory;
import com.room414.racingbets.bll.abstraction.services.HorseService;
import com.room414.racingbets.bll.concrete.services.HorseServiceImpl;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class HorseServiceFactoryImpl implements HorseServiceFactory {
    private UnitOfWorkFactory unitOfWorkFactory;

    HorseServiceFactoryImpl(UnitOfWorkFactory unitOfWorkFactory) {
        this.unitOfWorkFactory = unitOfWorkFactory;
    }

    @Override
    public HorseService create() {
        return new HorseServiceImpl(unitOfWorkFactory);
    }
}
