package com.room414.racingbets.web.command.impl;

import com.room414.racingbets.web.command.interfaces.Action;
import com.room414.racingbets.web.command.interfaces.ActionFactory;
import com.room414.racingbets.web.controller.factories.AccountControllerFactory;
import com.room414.racingbets.web.controller.impl.AccountController;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public class AccountActionFactory implements ActionFactory {
    private AccountControllerFactory accountControllerFactory;

    public AccountActionFactory(AccountControllerFactory accountControllerFactory) {
        this.accountControllerFactory = accountControllerFactory;
    }

    @Override
    public Action create(String name, Locale locale) {
        AccountController accountController = accountControllerFactory.create(locale);
        switch (name) {
            case "register": return accountController::register;
            case "loginClient": return accountController::loginClient;
            case "refreshClient": return accountController::loginClient;
            case "loginAdmin": return accountController::loginAdmin;
            case "refreshAdmin": return accountController::loginAdmin;
            case "setRoles": return accountController::setRoles;
            case "addMoney": return accountController::addMoney;
            case "confirmEmail": return accountController::confirmEmail;
            case "findById": return accountController::findById;
            case "find": return accountController::find;
            case "delete": return accountController::delete;
            default: throw new IllegalArgumentException("There is no Action named " + name);
        }
    }
}
