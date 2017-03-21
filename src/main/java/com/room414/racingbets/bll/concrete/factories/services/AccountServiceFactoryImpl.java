package com.room414.racingbets.bll.concrete.factories.services;

import com.room414.racingbets.bll.abstraction.factories.services.AccountServiceFactory;
import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.concrete.services.AccountServiceImpl;
import com.room414.racingbets.dal.abstraction.factories.TokenStorageFactory;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public class AccountServiceFactoryImpl implements AccountServiceFactory {
    private TokenStorageFactory tokenStorageFactory;

    AccountServiceFactoryImpl(TokenStorageFactory tokenStorageFactory) {
        this.tokenStorageFactory = tokenStorageFactory;
    }

    @Override
    public AccountService create() {
        return new AccountServiceImpl(tokenStorageFactory);
    }
}
