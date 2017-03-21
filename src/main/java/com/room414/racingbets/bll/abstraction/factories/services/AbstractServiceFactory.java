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
    OwnerServiceFactory createOwnerServiceFactory();
    ParticipantServiceFactory createParticipantServiceFactory();
    RacecourseServiceFactory createRacecourseServiceFactory();
    RaceServiceFactory createRaceServiceFactory();
    TrainerServiceFactory createTrainerServiceFactory();
    UserServiceFactory createUserServiceFactory();
}
