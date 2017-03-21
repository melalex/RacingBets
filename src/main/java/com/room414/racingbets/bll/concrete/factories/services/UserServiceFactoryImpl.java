package com.room414.racingbets.bll.concrete.factories.services;

import com.room414.racingbets.bll.abstraction.factories.services.UserServiceFactory;
import com.room414.racingbets.bll.abstraction.services.UserService;
import com.room414.racingbets.bll.concrete.services.UserServiceImpl;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class UserServiceFactoryImpl implements UserServiceFactory {
    private UnitOfWorkFactory unitOfWorkFactory;

    UserServiceFactoryImpl(UnitOfWorkFactory unitOfWorkFactory) {
        this.unitOfWorkFactory = unitOfWorkFactory;
    }

    @Override
    public UserService create() {
        return new UserServiceImpl(unitOfWorkFactory);
    }
}
