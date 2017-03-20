package com.room414.racingbets.bll.concrete.factories;

import com.room414.racingbets.bll.abstraction.factories.UserServiceFactory;
import com.room414.racingbets.bll.abstraction.services.UserService;
import com.room414.racingbets.bll.concrete.services.UserServiceImpl;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class UserServiceFactoryImpl implements UserServiceFactory {
    private UnitOfWorkFactory unitOfWorkFactory;

    public UserServiceFactoryImpl(UnitOfWorkFactory unitOfWorkFactory) {
        this.unitOfWorkFactory = unitOfWorkFactory;
    }

    @Override
    public UserService create() {
        return new UserServiceImpl(unitOfWorkFactory);
    }
}
