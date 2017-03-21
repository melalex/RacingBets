package com.room414.racingbets.bll.abstraction.factories.services;

import com.room414.racingbets.bll.abstraction.services.AccountService;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public interface AccountServiceFactory {
    AccountService create();
}
