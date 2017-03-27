package com.room414.racingbets.web.controller.factories;

import com.room414.racingbets.bll.abstraction.factories.services.AbstractServiceFactory;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public class ControllerFactoryCreator {
    private AbstractServiceFactory serviceFactory;

    public ControllerFactoryCreator(AbstractServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    public AccountControllerFactory createAccountControllerFactory() {
        return new AccountControllerFactory(
                serviceFactory.createAccountServiceFactory(),
                serviceFactory.createUserServiceFactory(),
                serviceFactory.createMessageServiceFactory()
        );
    }

    public BetControllerFactory createBetControllerFactory() {
        return new BetControllerFactory(
                serviceFactory.createBetServiceFactory(),
                serviceFactory.createAccountServiceFactory(),
                serviceFactory.createUserServiceFactory(),
                serviceFactory.createRaceServiceFactory()
        );
    }

    public HorseControllerFactory createHorseControllerFactory() {
        return new HorseControllerFactory(
                serviceFactory.createHorseServiceFactory(),
                serviceFactory.createAccountServiceFactory()
        );
    }

    public JockeyControllerFactory createJockeyControllerFactory() {
        return new JockeyControllerFactory(
                serviceFactory.createJockeyServiceFactory(),
                serviceFactory.createAccountServiceFactory()
        );
    }

    public OwnerControllerFactory createOwnerControllerFactory() {
        return new OwnerControllerFactory(
                serviceFactory.createOwnerServiceFactory(),
                serviceFactory.createAccountServiceFactory()
        );
    }

    public RaceControllerFactory createRaceControllerFactory() {
        return new RaceControllerFactory(
                serviceFactory.createRaceServiceFactory(),
                serviceFactory.createAccountServiceFactory()
        );
    }

    public RacecourseControllerFactory createRacecourseControllerFactory() {
        return new RacecourseControllerFactory(
                serviceFactory.createRacecourseServiceFactory(),
                serviceFactory.createAccountServiceFactory()
        );
    }

    public TrainerControllerFactory createTrainerControllerFactory() {
        return new TrainerControllerFactory(
                serviceFactory.createTrainerServiceFactory(),
                serviceFactory.createAccountServiceFactory()
        );
    }
}
