package com.room414.racingbets.web.command.impl;

import com.room414.racingbets.web.command.interfaces.Action;
import com.room414.racingbets.web.command.interfaces.ActionFactory;
import com.room414.racingbets.web.controller.interfaces.CrudController;
import com.room414.racingbets.web.controller.interfaces.CrudControllerFactory;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public class CrudActionFactory implements ActionFactory {
    private CrudControllerFactory crudControllerFactory;

    public CrudActionFactory(CrudControllerFactory crudControllerFactory) {
        this.crudControllerFactory = crudControllerFactory;
    }

    @Override
    public Action create(String name, Locale locale) {
        CrudController crudController = crudControllerFactory.create(locale);
        switch (name) {
            case "create": return crudController::create;
            case "update": return crudController::update;
            case "find": return crudController::find;
            case "search": return crudController::search;
            case "delete": return crudController::delete;
            default: throw new IllegalArgumentException("There is no Action named " + name);
        }
    }
}
