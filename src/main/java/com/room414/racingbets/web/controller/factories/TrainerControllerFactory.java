package com.room414.racingbets.web.controller.factories;

import com.room414.racingbets.bll.abstraction.factories.services.AccountServiceFactory;
import com.room414.racingbets.bll.abstraction.factories.services.TrainerServiceFactory;
import com.room414.racingbets.web.controller.impl.TrainerController;
import com.room414.racingbets.web.controller.interfaces.CrudControllerFactory;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public class TrainerControllerFactory implements CrudControllerFactory {
    private TrainerServiceFactory trainerServiceFactory;
    private AccountServiceFactory accountServiceFactory;

    TrainerControllerFactory(TrainerServiceFactory trainerServiceFactory, AccountServiceFactory accountServiceFactory) {
        this.trainerServiceFactory = trainerServiceFactory;
        this.accountServiceFactory = accountServiceFactory;
    }

    public TrainerController create(Locale locale) {
        return new TrainerController(
                trainerServiceFactory.create(),
                accountServiceFactory.create(),
                locale
        );
    }
}
