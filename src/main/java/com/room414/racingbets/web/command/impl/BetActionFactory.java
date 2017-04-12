package com.room414.racingbets.web.command.impl;

import com.room414.racingbets.web.command.interfaces.Action;
import com.room414.racingbets.web.command.interfaces.ActionFactory;
import com.room414.racingbets.web.controller.factories.BetControllerFactory;
import com.room414.racingbets.web.controller.impl.BetController;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public class BetActionFactory implements ActionFactory {
    private BetControllerFactory betControllerFactory;

    public BetActionFactory(BetControllerFactory betControllerFactory) {
        this.betControllerFactory = betControllerFactory;
    }

    @Override
    public Action create(String name, Locale locale) {
        BetController betController = betControllerFactory.create(locale);
        switch (name) {
            case "makeBet": return betController::makeBet;
            case "getOdds": return betController::getOdds;
            case "getBets": return betController::getBets;
            default: throw new IllegalArgumentException("There is no Action named " + name);
        }
    }
}
