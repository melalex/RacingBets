package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.resolvers.UnitOfWorkParameterResolver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 07 Mar 2017
 */
@ExtendWith(UnitOfWorkParameterResolver.class)
class JockeyDaoTest {
    private UnitOfWork unitOfWork;

    public JockeyDaoTest(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    private JockeyDao getJockeyDao() {
        return unitOfWork.getJockeyDao();
    }

    @AfterAll
    void tearDown() throws Exception {
        unitOfWork.close();
    }

    @Test
    void create() {

    }

    @Test
    void find() {

    }

    @Test
    void findAllLimitOffset() {

    }

    @Test
    void findAll() {

    }

    @Test
    void count() {

    }

    @Test
    void update() {

    }

    @Test
    void delete() {

    }

    @Test
    void findByNamePart() {

    }

    @Test
    void findByNamePartCount() {

    }
}