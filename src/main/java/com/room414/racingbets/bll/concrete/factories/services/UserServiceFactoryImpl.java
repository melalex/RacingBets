package com.room414.racingbets.bll.concrete.factories.services;

import com.room414.racingbets.bll.abstraction.factories.services.UserServiceFactory;
import com.room414.racingbets.bll.abstraction.services.UserService;
import com.room414.racingbets.bll.concrete.services.UserServiceImpl;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;

import java.util.Properties;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class UserServiceFactoryImpl implements UserServiceFactory {
    private UnitOfWorkFactory unitOfWorkFactory;
    private String randomAlgorithm;

    UserServiceFactoryImpl(UnitOfWorkFactory unitOfWorkFactory, Properties properties) {
        this.unitOfWorkFactory = unitOfWorkFactory;
        this.randomAlgorithm = properties.getProperty("bll.random");
    }

    @Override
    public UserService create() {
        // TODO: properties
        return new UserServiceImpl(unitOfWorkFactory, randomAlgorithm);
    }
}
