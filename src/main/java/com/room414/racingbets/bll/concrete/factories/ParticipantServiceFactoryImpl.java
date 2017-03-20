package com.room414.racingbets.bll.concrete.factories;

import com.room414.racingbets.bll.abstraction.factories.ParticipantServiceFactory;
import com.room414.racingbets.bll.abstraction.services.ParticipantService;
import com.room414.racingbets.bll.concrete.services.ParticipantServiceImpl;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class ParticipantServiceFactoryImpl implements ParticipantServiceFactory {
    private UnitOfWorkFactory unitOfWorkFactory;

    public ParticipantServiceFactoryImpl(UnitOfWorkFactory unitOfWorkFactory) {
        this.unitOfWorkFactory = unitOfWorkFactory;
    }

    @Override
    public ParticipantService create() {
        return new ParticipantServiceImpl(unitOfWorkFactory);
    }
}
