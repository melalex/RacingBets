package com.room414.racingbets.bll.concrete.factories.services;

import com.room414.racingbets.bll.abstraction.factories.services.TrainerServiceFactory;
import com.room414.racingbets.bll.abstraction.services.TrainerService;
import com.room414.racingbets.bll.concrete.services.TrainerServiceImpl;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class TrainerServiceFactoryImpl implements TrainerServiceFactory {
    private UnitOfWorkFactory unitOfWorkFactory;

    TrainerServiceFactoryImpl(UnitOfWorkFactory unitOfWorkFactory) {
        this.unitOfWorkFactory = unitOfWorkFactory;
    }

    @Override
    public TrainerService create() {
        return new TrainerServiceImpl(unitOfWorkFactory);
    }
}
