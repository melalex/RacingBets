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
    private String hashAlgorithm;
    private String encoding;

    UserServiceFactoryImpl(UnitOfWorkFactory unitOfWorkFactory, Properties properties) {
        this.unitOfWorkFactory = unitOfWorkFactory;
        this.randomAlgorithm = properties.getProperty("bll.random");
        this.hashAlgorithm = properties.getProperty("bll.hash");
        this.encoding = properties.getProperty("bll.encoding");
    }

    @Override
    public UserService create() {
        return new UserServiceImpl(unitOfWorkFactory, randomAlgorithm, hashAlgorithm, encoding);
    }
}
