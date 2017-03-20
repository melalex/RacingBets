package com.room414.racingbets.bll.concrete.factories;

import com.room414.racingbets.bll.abstraction.factories.*;
import com.room414.racingbets.dal.abstraction.factories.AbstractDalFactory;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class AbstractServiceFactoryImpl implements AbstractServiceFactory {
    private AbstractDalFactory dalFactory;

    private BetServiceFactory betServiceFactory;
    private HorseServiceFactory horseServiceFactory;
    private JockeyServiceFactory jockeyServiceFactory;
    private OwnerServiceFactory ownerServiceFactory;
    private ParticipantServiceFactory participantServiceFactory;
    private RacecourseServiceFactory racecourseServiceFactory;
    private RaceServiceFactory raceServiceFactory;
    private TrainerServiceFactory trainerServiceFactory;
    private UserServiceFactory userServiceFactory;

    public AbstractServiceFactoryImpl(AbstractDalFactory dalFactory) {
        this.dalFactory = dalFactory;

        this.betServiceFactory = new BetServiceFactoryImpl(dalFactory.createUnitOfWorkFactory());
        this.horseServiceFactory = new HorseServiceFactoryImpl(dalFactory.createUnitOfWorkFactory());
        this.jockeyServiceFactory = new JockeyServiceFactoryImpl(dalFactory.createUnitOfWorkFactory());
        this.ownerServiceFactory = new OwnerServiceFactoryImpl(dalFactory.createUnitOfWorkFactory());
        this.participantServiceFactory = new ParticipantServiceFactoryImpl(dalFactory.createUnitOfWorkFactory());
        this.racecourseServiceFactory = new RacecourseServiceFactoryImpl(dalFactory.createUnitOfWorkFactory());
        this.raceServiceFactory = new RaceServiceFactoryImpl(dalFactory.createUnitOfWorkFactory());
        this.trainerServiceFactory = new TrainerServiceFactoryImpl(dalFactory.createUnitOfWorkFactory());
        this.userServiceFactory = new UserServiceFactoryImpl(dalFactory.createUnitOfWorkFactory());
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
