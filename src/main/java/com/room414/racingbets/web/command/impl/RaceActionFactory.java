package com.room414.racingbets.web.command.impl;

import com.room414.racingbets.web.command.interfaces.Action;
import com.room414.racingbets.web.command.interfaces.ActionFactory;
import com.room414.racingbets.web.controller.factories.RaceControllerFactory;
import com.room414.racingbets.web.controller.impl.RaceController;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public class RaceActionFactory implements ActionFactory {
    private RaceControllerFactory raceControllerFactory;

    public RaceActionFactory(RaceControllerFactory raceControllerFactory) {
        this.raceControllerFactory = raceControllerFactory;
    }

    @Override
    public Action create(String name, Locale locale) {
        RaceController raceController = raceControllerFactory.create(locale);
        switch (name) {
            case "scheduleRace": return raceController::scheduleRace;
            case "startRace": return raceController::startRace;
            case "rejectRace": return raceController::rejectRace;
            case "finishRace": return raceController::finishRace;
            case "update": return raceController::update;
            case "delete": return raceController::delete;
            case "find": return raceController::find;
            case "filter": return raceController::filter;
            default: throw new IllegalArgumentException("There is no Action named " + name);
        }
    }
}
