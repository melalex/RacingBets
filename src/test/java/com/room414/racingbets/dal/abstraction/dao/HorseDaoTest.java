package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Owner;
import com.room414.racingbets.dal.domain.entities.Trainer;
import com.room414.racingbets.dal.resolvers.UnitOfWorkParameterResolver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.text.ParseException;

import static com.room414.racingbets.dal.infrastructure.TestHelper.sqlDateFromString;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author melalex
 * @version 1.0 08 Mar 2017
 */
@ExtendWith(UnitOfWorkParameterResolver.class)
class HorseDaoTest {
    private static UnitOfWork unitOfWork;

    @BeforeAll
    static void setUp(UnitOfWork unitOfWork) {
        HorseDaoTest.unitOfWork = unitOfWork;
    }

    @AfterAll
    static void tearDown() throws Exception {
        unitOfWork.close();
    }

    private static HorseDao getHorseDao() {
        return unitOfWork.getHorseDao();
    }

    @Test
    void find_damAndSirNull_returnedEntity() throws ParseException, DalException {
        HorseDao horseDao = getHorseDao();

        Owner owner = Owner.builder()
                .setId(1)
                .setFirstName("Ruby")
                .setSecondName("Nichols")
                .setBirthday(sqlDateFromString("1982-04-21"))
                .build();

        Trainer trainer = Trainer.builder()
                .setId(5)
                .setFirstName("Alex")
                .setSecondName("Strutynski")
                .setBirthday(sqlDateFromString("1980-04-21"))
                .build();

        Horse expectedResult = Horse.builder()
                .setId(1)
                .setName("Fixflex")
                .setBirthday(sqlDateFromString("2008-02-22"))
                .setGender("mare")
                .setTrainer(trainer)
                .setOwner(owner)
                .build();

        Horse result = horseDao.find(1L);

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void find_existent_returnedEntity() throws ParseException, DalException {
        HorseDao horseDao = getHorseDao();

        Horse expectedResult = Horse.builder()
                .setId(1)
                .setName("Fixflex")
                .setBirthday(sqlDateFromString("2008-02-22"))
                .setGender("mare")
                .setTrainerById(5)
                .setOwnerById(1)
                .build();

        Horse result = horseDao.find(1L);

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void find_nonexistent_returnedNull() {

    }

    @Test
    void findAllLimitOffset() {
    }

    @Test
    void findAllLimitOffset_nonexistent_returnedEmptyList() {

    }

    @Test
    void findAll() {
    }

    @Test
    void count() {
    }

    @Test
    void findByNamePart_existent_returnedList() {
    }

    @Test
    void findByNamePart_nonexistent_returnedEmptyList() {

    }

    @Test
    void findByNamePartCount() {

    }

    @Test
    void createAndDelete() {

    }

    @Test
    void update() {

    }
}