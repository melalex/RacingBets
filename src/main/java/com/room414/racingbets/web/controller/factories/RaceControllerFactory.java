package com.room414.racingbets.web.controller.factories;

import com.room414.racingbets.bll.abstraction.factories.services.AccountServiceFactory;
import com.room414.racingbets.bll.abstraction.factories.services.RaceServiceFactory;
import com.room414.racingbets.web.controller.impl.RaceController;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public class RaceControllerFactory {
    private RaceServiceFactory raceServiceFactory;
    private AccountServiceFactory accountServiceFactory;

    RaceControllerFactory(RaceServiceFactory raceServiceFactory, AccountServiceFactory accountServiceFactory) {
        this.raceServiceFactory = raceServiceFactory;
        this.accountServiceFactory = accountServiceFactory;
    }

    public RaceController create(Locale locale) {
        return new RaceController(
                raceServiceFactory.create(),
                accountServiceFactory.create(),
                locale
        );
    }
}
