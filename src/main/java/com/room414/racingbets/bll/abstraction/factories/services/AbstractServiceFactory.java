package com.room414.racingbets.bll.abstraction.factories.services;


/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public interface AbstractServiceFactory {
    AccountServiceFactory createAccountServiceFactory();
    BetServiceFactory createBetServiceFactory();
    HorseServiceFactory createHorseServiceFactory();
    JockeyServiceFactory createJockeyServiceFactory();
    MessageServiceFactory createMessageServiceFactory();
    OwnerServiceFactory createOwnerServiceFactory();
    RacecourseServiceFactory createRacecourseServiceFactory();
    RaceServiceFactory createRaceServiceFactory();
    TrainerServiceFactory createTrainerServiceFactory();
    UserServiceFactory createUserServiceFactory();
}
