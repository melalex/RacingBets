package com.room414.racingbets.web.controller.factories;

import com.room414.racingbets.bll.abstraction.factories.services.AccountServiceFactory;
import com.room414.racingbets.bll.abstraction.factories.services.BetServiceFactory;
import com.room414.racingbets.bll.abstraction.factories.services.RaceServiceFactory;
import com.room414.racingbets.bll.abstraction.factories.services.UserServiceFactory;
import com.room414.racingbets.web.controller.impl.BetController;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public class BetControllerFactory {
    private BetServiceFactory betServiceFactory;
    private AccountServiceFactory accountServiceFactory;
    private UserServiceFactory userServiceFactory;
    private RaceServiceFactory raceServiceFactory;

    BetControllerFactory(
            BetServiceFactory betServiceFactory,
            AccountServiceFactory accountServiceFactory,
            UserServiceFactory userServiceFactory,
            RaceServiceFactory raceServiceFactory
    ) {
        this.betServiceFactory = betServiceFactory;
        this.accountServiceFactory = accountServiceFactory;
        this.userServiceFactory = userServiceFactory;
        this.raceServiceFactory = raceServiceFactory;
    }

    public BetController create(Locale locale) {
        return new BetController(
                betServiceFactory.create(),
                accountServiceFactory.create(),
                userServiceFactory.create(),
                raceServiceFactory.create(),
                locale
        );
    }

}
