package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.Jockey;
import com.room414.racingbets.dal.resolvers.UnitOfWorkParameterResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 07 Mar 2017
 */
@ExtendWith(UnitOfWorkParameterResolver.class)
class JockeyDaoTest {
    private UnitOfWorkFactory unitOfWorkFactory;

    public JockeyDaoTest(UnitOfWorkFactory unitOfWorkFactory) {
        this.unitOfWorkFactory = unitOfWorkFactory;
    }

    @Test
    void create() {

    }

    @Test
    void find() throws Exception {
        try (UnitOfWork unitOfWork = unitOfWorkFactory.create()) {
            JockeyDao jockeyDao = unitOfWork.getJockeyDao();
            Jockey expectedResult = Jockey.builder()
                    .setId(1)
                    .setFirstName("Ruby")
                    .setSecondName("Nichols")
                    .setBirthday(new Date(388195200))
                    .build();

            Jockey jockey = jockeyDao.find(1L);

            assert jockey.equals(expectedResult) : "jockey != expectedResult";
        }
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