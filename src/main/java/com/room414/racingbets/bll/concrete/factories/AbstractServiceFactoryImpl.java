package com.room414.racingbets.bll.concrete.factories;

import com.room414.racingbets.bll.abstraction.factories.*;
import com.room414.racingbets.dal.abstraction.facade.DalFacade;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class AbstractServiceFactoryImpl implements AbstractServiceFactory {
    private BetServiceFactory betServiceFactory;
    private HorseServiceFactory horseServiceFactory;
    private JockeyServiceFactory jockeyServiceFactory;
    private OwnerServiceFactory ownerServiceFactory;
    private ParticipantServiceFactory participantServiceFactory;
    private RacecourseServiceFactory racecourseServiceFactory;
    private RaceServiceFactory raceServiceFactory;
    private TrainerServiceFactory trainerServiceFactory;
    private UserServiceFactory userServiceFactory;

    public AbstractServiceFactoryImpl(DalFacade dalFactory) {
        this.betServiceFactory = new BetServiceFactoryImpl(dalFactory.getUnitOfWorkFactory());
        this.horseServiceFactory = new HorseServiceFactoryImpl(dalFactory.getUnitOfWorkFactory());
        this.jockeyServiceFactory = new JockeyServiceFactoryImpl(dalFactory.getUnitOfWorkFactory());
        this.ownerServiceFactory = new OwnerServiceFactoryImpl(dalFactory.getUnitOfWorkFactory());
        this.participantServiceFactory = new ParticipantServiceFactoryImpl(dalFactory.getUnitOfWorkFactory());
        this.racecourseServiceFactory = new RacecourseServiceFactoryImpl(dalFactory.getUnitOfWorkFactory());
        this.raceServiceFactory = new RaceServiceFactoryImpl(dalFactory.getUnitOfWorkFactory());
        this.trainerServiceFactory = new TrainerServiceFactoryImpl(dalFactory.getUnitOfWorkFactory());
        this.userServiceFactory = new UserServiceFactoryImpl(dalFactory.getUnitOfWorkFactory());
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
