package com.room414.racingbets.bll.concrete.factories.services;

import com.room414.racingbets.bll.abstraction.factories.infrastructure.MessengerFactory;
import com.room414.racingbets.bll.abstraction.factories.services.*;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class AbstractServiceFactoryImpl implements AbstractServiceFactory {
    private AccountServiceFactory accountServiceFactory;
    private BetServiceFactory betServiceFactory;
    private HorseServiceFactory horseServiceFactory;
    private JockeyServiceFactory jockeyServiceFactory;
    private OwnerServiceFactory ownerServiceFactory;
    private ParticipantServiceFactory participantServiceFactory;
    private RacecourseServiceFactory racecourseServiceFactory;
    private RaceServiceFactory raceServiceFactory;
    private TrainerServiceFactory trainerServiceFactory;
    private UserServiceFactory userServiceFactory;

    public AbstractServiceFactoryImpl(UnitOfWorkFactory unitOfWorkFactory, MessengerFactory messengerFactory) {
        this.accountServiceFactory = new AccountServiceFactoryImpl();
        this.betServiceFactory = new BetServiceFactoryImpl(unitOfWorkFactory);
        this.horseServiceFactory = new HorseServiceFactoryImpl(unitOfWorkFactory);
        this.jockeyServiceFactory = new JockeyServiceFactoryImpl(unitOfWorkFactory);
        this.ownerServiceFactory = new OwnerServiceFactoryImpl(unitOfWorkFactory);
        this.participantServiceFactory = new ParticipantServiceFactoryImpl(unitOfWorkFactory);
        this.racecourseServiceFactory = new RacecourseServiceFactoryImpl(unitOfWorkFactory);
        this.raceServiceFactory = new RaceServiceFactoryImpl(unitOfWorkFactory, messengerFactory);
        this.trainerServiceFactory = new TrainerServiceFactoryImpl(unitOfWorkFactory);
        this.userServiceFactory = new UserServiceFactoryImpl(unitOfWorkFactory, messengerFactory);
    }

    @Override
    public AccountServiceFactory createAccountServiceFactory() {
        return accountServiceFactory;
    }

    @Override
    public BetServiceFactory createBetServiceFactory() {
        return betServiceFactory;
    }

    @Override
    public HorseServiceFactory createHorseServiceFactory() {
        return horseServiceFactory;
    }

    @Override
    public JockeyServiceFactory createJockeyServiceFactory() {
        return jockeyServiceFactory;
    }

    @Override
    public OwnerServiceFactory createOwnerServiceFactory() {
        return ownerServiceFactory;
    }

    @Override
    public ParticipantServiceFactory createParticipantServiceFactory() {
        return participantServiceFactory;
    }

    @Override
    public RacecourseServiceFactory createRacecourseServiceFactory() {
        return racecourseServiceFactory;
    }

    @Override
    public RaceServiceFactory createRaceServiceFactory() {
        return raceServiceFactory;
    }

    @Override
    public TrainerServiceFactory createTrainerServiceFactory() {
        return trainerServiceFactory;
    }

    @Override
    public UserServiceFactory createUserServiceFactory() {
        return userServiceFactory;
    }
}
