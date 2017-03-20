package com.room414.racingbets.bll.concrete.factories.services;

import com.room414.racingbets.bll.abstraction.factories.services.RacecourseServiceFactory;
import com.room414.racingbets.bll.abstraction.services.RacecourseService;
import com.room414.racingbets.bll.concrete.services.RacecourseServiceImpl;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class RacecourseServiceFactoryImpl implements RacecourseServiceFactory {
    private UnitOfWorkFactory unitOfWorkFactory;

    RacecourseServiceFactoryImpl(UnitOfWorkFactory unitOfWorkFactory) {
        this.unitOfWorkFactory = unitOfWorkFactory;
    }

    @Override
    public RacecourseService create() {
        return new RacecourseServiceImpl(unitOfWorkFactory);
    }
}
