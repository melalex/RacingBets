package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.Jockey;
import com.room414.racingbets.dal.resolvers.UnitOfWorkParameterResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import static com.room414.racingbets.dal.infrastructure.TestHelper.sqlDateFromString;
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
                    .setBirthday(sqlDateFromString("1982-04-21"))
                    .build();

            Jockey jockey = jockeyDao.find(1L);

            assert jockey.equals(expectedResult) : "jockey != expectedResult";
        }
    }

    @Test
    void findAllLimitOffset() {

    }

    @Test
    void findAll() throws Exception {
        try (UnitOfWork unitOfWork = unitOfWorkFactory.create()) {
            JockeyDao jockeyDao = unitOfWork.getJockeyDao();
            List<Jockey> expectedResult = new LinkedList<>();

            expectedResult.add(
                    Jockey.builder()
                    .setId(1)
                    .setFirstName("Ruby")
                    .setSecondName("Nichols")
                    .setBirthday(sqlDateFromString("1982-04-21"))
                    .build()
            );
            expectedResult.add(
                    Jockey.builder()
                            .setId(2)
                            .setFirstName("Nichols")
                            .setSecondName("Ruby")
                            .setBirthday(sqlDateFromString("1962-05-19"))
                            .build()
            );
            expectedResult.add(
                    Jockey.builder()
                            .setId(3)
                            .setFirstName("Doris")
                            .setSecondName("Franklin")
                            .setBirthday(sqlDateFromString("1984-03-16"))
                            .build()
            );
            expectedResult.add(
                    Jockey.builder()
                            .setId(4)
                            .setFirstName("Thomas")
                            .setSecondName("West")
                            .setBirthday(sqlDateFromString("1980-01-19"))
                            .build()
            );
            expectedResult.add(
                    Jockey.builder()
                            .setId(5)
                            .setFirstName("Matthew")
                            .setSecondName("Taylor")
                            .setBirthday(sqlDateFromString("1995-01-15"))
                            .build()
            );

            List<Jockey> jockeys = jockeyDao.findAll();

            assert jockeys.equals(expectedResult) : "jockeys != expectedResult";
        }
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