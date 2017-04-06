package com.room414.racingbets.web.command.impl;

import com.room414.racingbets.web.command.interfaces.Action;
import com.room414.racingbets.web.command.interfaces.ActionFactory;
import com.room414.racingbets.web.controller.factories.ViewControllerFactory;
import com.room414.racingbets.web.controller.impl.ViewController;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 06 Apr 2017
 */
public class ViewActionFactory implements ActionFactory {
    private ViewControllerFactory viewControllerFactory;

    public ViewActionFactory(ViewControllerFactory viewControllerFactory) {
        this.viewControllerFactory = viewControllerFactory;
    }

    @Override
    public Action create(String name, Locale locale) {
        ViewController viewController = viewControllerFactory.create();
        switch (name) {
            case "returnAdminPage": return viewController::returnAdminPage;
            case "returnClientPage": return viewController::returnClientPage;
            default: throw new IllegalArgumentException("There is no Action named " + name);
        }
    }
}
