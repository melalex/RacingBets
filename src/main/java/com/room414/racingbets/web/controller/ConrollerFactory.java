package com.room414.racingbets.web.controller;

import com.room414.racingbets.bll.abstraction.factories.services.AbstractServiceFactory;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public class ConrollerFactory {
    private AbstractServiceFactory serviceFactory;

    public ConrollerFactory(AbstractServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    public AccountController createAccountController(Locale locale) {
        return new AccountController(
                serviceFactory.createAccountServiceFactory().create(),
                serviceFactory.createUserServiceFactory().create(),
                serviceFactory.createMessageServiceFactory().getMessageService(),
                locale
        );
    }

    public BetController createBetController(Locale locale) {
        return new BetController(
                serviceFactory.createBetServiceFactory().create(),
                serviceFactory.createAccountServiceFactory().create(),
                serviceFactory.createUserServiceFactory().create(),
                serviceFactory.createRaceServiceFactory().create(),
                locale
        );
    }

    public HorseController createHorseController(Locale locale) {
        return new HorseController(
                serviceFactory.createHorseServiceFactory().create(),
                serviceFactory.createAccountServiceFactory().create(),
                locale
        );
    }

    public JockeyController createJockeyController(Locale locale) {
        return new JockeyController(
                serviceFactory.createJockeyServiceFactory().create(),
                serviceFactory.createAccountServiceFactory().create(),
                locale
        );
    }

    public OwnerController createOwnerController(Locale locale) {
        return new OwnerController(
                serviceFactory.createOwnerServiceFactory().create(),
                serviceFactory.createAccountServiceFactory().create(),
                locale
        );
    }

    public RaceController createRaceController(Locale locale) {
        return new RaceController(
                serviceFactory.createRaceServiceFactory().create(),
                serviceFactory.createAccountServiceFactory().create(),
                locale
        );
    }

    public RacecourseController createRacecourseController(Locale locale) {
        return new RacecourseController(
                serviceFactory.createRacecourseServiceFactory().create(),
                serviceFactory.createAccountServiceFactory().create(),
                locale
        );
    }

    public TrainerController createTrainerController(Locale locale) {
        return new TrainerController(
                serviceFactory.createTrainerServiceFactory().create(),
                serviceFactory.createAccountServiceFactory().create(),
                locale
        );
    }
}
