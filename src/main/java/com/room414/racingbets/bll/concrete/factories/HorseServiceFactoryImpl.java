package com.room414.racingbets.bll.concrete.factories;

import com.room414.racingbets.bll.abstraction.factories.HorseServiceFactory;
import com.room414.racingbets.bll.abstraction.services.HorseService;
import com.room414.racingbets.bll.concrete.services.HorseServiceImpl;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class HorseServiceFactoryImpl implements HorseServiceFactory {
    private UnitOfWorkFactory unitOfWorkFactory;

    public HorseServiceFactoryImpl(UnitOfWorkFactory unitOfWorkFactory) {
        this.unitOfWorkFactory = unitOfWorkFactory;
    }

    @Override
    public HorseService create() {
        return new HorseServiceImpl(unitOfWorkFactory);
    }
}
