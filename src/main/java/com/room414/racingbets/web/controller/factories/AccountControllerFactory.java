package com.room414.racingbets.web.controller.factories;

import com.room414.racingbets.bll.abstraction.factories.services.AccountServiceFactory;
import com.room414.racingbets.bll.abstraction.factories.services.MessageServiceFactory;
import com.room414.racingbets.bll.abstraction.factories.services.UserServiceFactory;
import com.room414.racingbets.web.controller.impl.AccountController;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public class AccountControllerFactory {
    private AccountServiceFactory accountServiceFactory;
    private UserServiceFactory userServiceFactory;
    private MessageServiceFactory messageServiceFactory;

    AccountControllerFactory(
            AccountServiceFactory accountServiceFactory,
            UserServiceFactory userServiceFactory,
            MessageServiceFactory messageServiceFactory
    ) {
        this.accountServiceFactory = accountServiceFactory;
        this.userServiceFactory = userServiceFactory;
        this.messageServiceFactory = messageServiceFactory;
    }

    public AccountController create(Locale locale) {
        return new AccountController(
                accountServiceFactory.create(),
                userServiceFactory.create(),
                messageServiceFactory.getMessageService(),
                locale
        );
    }
}
