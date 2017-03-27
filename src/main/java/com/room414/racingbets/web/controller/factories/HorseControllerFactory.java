package com.room414.racingbets.web.controller.factories;

import com.room414.racingbets.bll.abstraction.factories.services.AccountServiceFactory;
import com.room414.racingbets.bll.abstraction.factories.services.HorseServiceFactory;
import com.room414.racingbets.web.controller.impl.HorseController;
import com.room414.racingbets.web.controller.interfaces.CrudControllerFactory;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public class HorseControllerFactory implements CrudControllerFactory {
    private HorseServiceFactory horseServiceFactory;
    private AccountServiceFactory accountServiceFactory;

    HorseControllerFactory(HorseServiceFactory horseServiceFactory, AccountServiceFactory accountServiceFactory) {
        this.horseServiceFactory = horseServiceFactory;
        this.accountServiceFactory = accountServiceFactory;
    }

    public HorseController create(Locale locale) {
        return new HorseController(
                horseServiceFactory.create(),
                accountServiceFactory.create(),
                locale
        );
    }
}
