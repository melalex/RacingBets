package com.room414.racingbets.bll.concrete.factories.services;

import com.room414.racingbets.bll.abstraction.factories.infrastructure.JwtFactory;
import com.room414.racingbets.bll.abstraction.factories.services.MessageServiceFactory;
import com.room414.racingbets.bll.abstraction.factories.services.*;
import com.room414.racingbets.dal.abstraction.factories.AbstractDalFactory;

import java.util.Properties;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class AbstractServiceFactoryImpl implements AbstractServiceFactory {
    private AccountServiceFactory accountServiceFactory;
    private BetServiceFactory betServiceFactory;
    private HorseServiceFactory horseServiceFactory;
    private JockeyServiceFactory jockeyServiceFactory;
    private MessageServiceFactory messageServiceFactory;
    private OwnerServiceFactory ownerServiceFactory;
    private RacecourseServiceFactory racecourseServiceFactory;
    private RaceServiceFactory raceServiceFactory;
    private TrainerServiceFactory trainerServiceFactory;
    private UserServiceFactory userServiceFactory;

    public AbstractServiceFactoryImpl(
            AbstractDalFactory dalFactory,
            JwtFactory jwtFactory,
            Properties mail,
            Properties bll
    ) {
        this.messageServiceFactory = new MessageServiceFactoryImpl(mail);
        this.userServiceFactory = new UserServiceFactoryImpl(dalFactory.getUnitOfWorkFactory(), bll);
        this.raceServiceFactory = new RaceServiceFactoryImpl(dalFactory.getUnitOfWorkFactory(), messageServiceFactory, bll);

        this.accountServiceFactory = new AccountServiceFactoryImpl(dalFactory.getTokenStorageFactory(), jwtFactory);
        this.betServiceFactory = new BetServiceFactoryImpl(dalFactory.getUnitOfWorkFactory());
        this.horseServiceFactory = new HorseServiceFactoryImpl(dalFactory.getUnitOfWorkFactory());
        this.jockeyServiceFactory = new JockeyServiceFactoryImpl(dalFactory.getUnitOfWorkFactory());
        this.ownerServiceFactory = new OwnerServiceFactoryImpl(dalFactory.getUnitOfWorkFactory());
        this.racecourseServiceFactory = new RacecourseServiceFactoryImpl(dalFactory.getUnitOfWorkFactory());
        this.trainerServiceFactory = new TrainerServiceFactoryImpl(dalFactory.getUnitOfWorkFactory());
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
    public MessageServiceFactory createMessageServiceFactory() {
        return messageServiceFactory;
    }

    @Override
    public OwnerServiceFactory createOwnerServiceFactory() {
        return ownerServiceFactory;
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
