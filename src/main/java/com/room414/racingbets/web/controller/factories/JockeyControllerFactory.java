package com.room414.racingbets.web.controller.factories;

import com.room414.racingbets.bll.abstraction.factories.services.AccountServiceFactory;
import com.room414.racingbets.bll.abstraction.factories.services.JockeyServiceFactory;
import com.room414.racingbets.web.controller.impl.JockeyController;
import com.room414.racingbets.web.controller.interfaces.CrudControllerFactory;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public class JockeyControllerFactory implements CrudControllerFactory {
    private JockeyServiceFactory jockeyServiceFactory;
    private AccountServiceFactory accountServiceFactory;

    JockeyControllerFactory(JockeyServiceFactory jockeyServiceFactory, AccountServiceFactory accountServiceFactory) {
        this.jockeyServiceFactory = jockeyServiceFactory;
        this.accountServiceFactory = accountServiceFactory;
    }

    public JockeyController create(Locale locale) {
        return new JockeyController(
                jockeyServiceFactory.create(),
                accountServiceFactory.create(),
                locale
        );
    }

}
