package com.room414.racingbets.bll.concrete.factories;

import com.room414.racingbets.bll.abstraction.factories.RaceServiceFactory;
import com.room414.racingbets.bll.abstraction.services.RaceService;
import com.room414.racingbets.bll.concrete.services.RaceServiceImpl;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class RaceServiceFactoryImpl implements RaceServiceFactory {
    private UnitOfWorkFactory unitOfWorkFactory;

    public RaceServiceFactoryImpl(UnitOfWorkFactory unitOfWorkFactory) {
        this.unitOfWorkFactory = unitOfWorkFactory;
    }

    @Override
    public RaceService create() {
        return new RaceServiceImpl(unitOfWorkFactory);
    }
}
