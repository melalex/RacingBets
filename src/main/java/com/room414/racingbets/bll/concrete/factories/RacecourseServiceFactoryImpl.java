package com.room414.racingbets.bll.concrete.factories;

import com.room414.racingbets.bll.abstraction.factories.RacecourseServiceFactory;
import com.room414.racingbets.bll.abstraction.services.RacecourseService;
import com.room414.racingbets.bll.concrete.services.RacecourseServiceImpl;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class RacecourseServiceFactoryImpl implements RacecourseServiceFactory {
    private UnitOfWorkFactory unitOfWorkFactory;

    public RacecourseServiceFactoryImpl(UnitOfWorkFactory unitOfWorkFactory) {
        this.unitOfWorkFactory = unitOfWorkFactory;
    }

    @Override
    public RacecourseService create() {
        return new RacecourseServiceImpl(unitOfWorkFactory);
    }
}
