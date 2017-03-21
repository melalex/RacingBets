package com.room414.racingbets.bll.concrete.factories.services;

import com.room414.racingbets.bll.abstraction.factories.services.MessageServiceFactory;
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
    private MessageServiceFactory messageServiceFactory;
    private int betsPerQuery;

    RaceServiceFactoryImpl(UnitOfWorkFactory unitOfWorkFactory, MessageServiceFactory messageServiceFactory, int betsPerQuery) {
        this.unitOfWorkFactory = unitOfWorkFactory;
        this.messageServiceFactory = messageServiceFactory;
        this.betsPerQuery = betsPerQuery;
    }

    @Override
    public RaceService create() {
        return new RaceServiceImpl(unitOfWorkFactory, messageServiceFactory.createRaceResultMessageService(), betsPerQuery);
    }
}
