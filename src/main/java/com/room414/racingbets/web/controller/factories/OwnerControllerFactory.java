package com.room414.racingbets.web.controller.factories;

import com.room414.racingbets.bll.abstraction.factories.services.AccountServiceFactory;
import com.room414.racingbets.bll.abstraction.factories.services.OwnerServiceFactory;
import com.room414.racingbets.web.controller.impl.OwnerController;
import com.room414.racingbets.web.controller.interfaces.CrudControllerFactory;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public class OwnerControllerFactory implements CrudControllerFactory {
    private OwnerServiceFactory ownerServiceFactory;
    private AccountServiceFactory accountServiceFactory;

    OwnerControllerFactory(OwnerServiceFactory ownerServiceFactory, AccountServiceFactory accountServiceFactory) {
        this.ownerServiceFactory = ownerServiceFactory;
        this.accountServiceFactory = accountServiceFactory;
    }

    public OwnerController create(Locale locale) {
        return new OwnerController(
                ownerServiceFactory.create(),
                accountServiceFactory.create(),
                locale
        );
    }

}
