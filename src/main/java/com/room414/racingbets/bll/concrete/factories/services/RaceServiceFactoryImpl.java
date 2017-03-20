package com.room414.racingbets.bll.concrete.factories.services;

import com.room414.racingbets.bll.abstraction.factories.infrastructure.MessengerFactory;
import com.room414.racingbets.bll.abstraction.factories.services.RaceServiceFactory;
import com.room414.racingbets.bll.abstraction.services.RaceService;
import com.room414.racingbets.bll.concrete.services.RaceServiceImpl;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class RaceServiceFactoryImpl implements RaceServiceFactory {
    private UnitOfWorkFactory unitOfWorkFactory;
    private MessengerFactory messengerFactory;

    RaceServiceFactoryImpl(UnitOfWorkFactory unitOfWorkFactory, MessengerFactory messengerFactory) {
        this.unitOfWorkFactory = unitOfWorkFactory;
        this.messengerFactory = messengerFactory;
    }

    @Override
    public RaceService create() {
        return new RaceServiceImpl(unitOfWorkFactory, messengerFactory.createRaceResultMessenger());
    }
}
