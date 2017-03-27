package com.room414.racingbets.web.controller.factories;

import com.room414.racingbets.bll.abstraction.factories.services.AccountServiceFactory;
import com.room414.racingbets.bll.abstraction.factories.services.RacecourseServiceFactory;
import com.room414.racingbets.web.controller.impl.RacecourseController;
import com.room414.racingbets.web.controller.interfaces.CrudControllerFactory;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public class RacecourseControllerFactory implements CrudControllerFactory {
    private RacecourseServiceFactory racecourseServiceFactory;
    private AccountServiceFactory accountServiceFactory;

    RacecourseControllerFactory(RacecourseServiceFactory racecourseServiceFactory, AccountServiceFactory accountServiceFactory) {
        this.racecourseServiceFactory = racecourseServiceFactory;
        this.accountServiceFactory = accountServiceFactory;
    }

    public RacecourseController create(Locale locale) {
        return new RacecourseController(
                racecourseServiceFactory.create(),
                accountServiceFactory.create(),
                locale
        );
    }

}
