package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.resolvers.UnitOfWorkParameterResolver;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @author Alexander Melashchenko
 * @version 1.0 07 Mar 2017
 */
@ExtendWith(UnitOfWorkParameterResolver.class)
class PersonDaoTest {
    private JockeyDao jockeyDao;
    private OwnerDao ownerDao;
    private TrainerDao trainerDao;

    public PersonDaoTest(UnitOfWork unitOfWork) {
        this.jockeyDao = unitOfWork.getJockeyDao();
        this.ownerDao = unitOfWork.getOwnerDao();
        this.trainerDao = unitOfWork.getTrainerDao();
    }


}