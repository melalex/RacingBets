package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.builders.PersonBuilder;
import com.room414.racingbets.dal.abstraction.entities.Person;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Jockey;
import com.room414.racingbets.dal.domain.entities.Owner;
import com.room414.racingbets.dal.domain.entities.Trainer;
import com.room414.racingbets.dal.resolvers.UnitOfWorkParameterResolver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.room414.racingbets.dal.infrastructure.TestHelper.sqlDateFromString;

/**
 * @author Alexander Melashchenko
 * @version 1.0 07 Mar 2017
 */
@ExtendWith(UnitOfWorkParameterResolver.class)
class PersonDaoTest {
    private static UnitOfWork unitOfWork;

    @BeforeAll
    static void setUp(UnitOfWork unitOfWork) {
        PersonDaoTest.unitOfWork = unitOfWork;
    }

    @AfterAll
    static void tearDown() throws Exception {
        unitOfWork.close();
    }

    @TestFactory
    List<DynamicTest> find_existent_returnedPerson_tests() throws Exception {
        return Arrays.asList(
                DynamicTest.dynamicTest(
                        "find_existent_returnedPerson for Jockey",
                        () -> find_existent_returnedPerson(unitOfWork.getJockeyDao(), Jockey.builder())
                ),
                DynamicTest.dynamicTest(
                        "find_existent_returnedPerson for Trainer",
                        () -> find_existent_returnedPerson(unitOfWork.getTrainerDao(), Trainer.builder())
                ),
                DynamicTest.dynamicTest(
                        "find_existent_returnedPerson for Owner",
                        () -> find_existent_returnedPerson(unitOfWork.getOwnerDao(), Owner.builder())
                )
        );
    }

    @TestFactory
    List<DynamicTest> find_nonexistent_returnedNull_tests() throws Exception {
        return Arrays.asList(
                DynamicTest.dynamicTest(
                        "find_nonexistent_returnedNull for Jockey",
                        () -> find_nonexistent_returnedNull(unitOfWork.getJockeyDao())
                ),
                DynamicTest.dynamicTest(
                        "find_nonexistent_returnedNull for Trainer",
                        () -> find_nonexistent_returnedNull(unitOfWork.getTrainerDao())
                ),
                DynamicTest.dynamicTest(
                        "find_nonexistent_returnedNull for Owner",
                        () -> find_nonexistent_returnedNull(unitOfWork.getOwnerDao())
                )
        );
    }

    @TestFactory
    List<DynamicTest> findAllLimitOffset_tests() throws Exception {
        return Arrays.asList(
                DynamicTest.dynamicTest(
                        "findAllLimitOffset for Jockey",
                        () -> findAllLimitOffset(unitOfWork.getJockeyDao(), Jockey.builder())
                ),
                DynamicTest.dynamicTest(
                        "findAllLimitOffset for Trainer",
                        () -> findAllLimitOffset(unitOfWork.getTrainerDao(), Trainer.builder())
                ),
                DynamicTest.dynamicTest(
                        "findAllLimitOffset for Owner",
                        () -> findAllLimitOffset(unitOfWork.getOwnerDao(), Owner.builder())
                )
        );
    }

    @TestFactory
    List<DynamicTest> findAllLimitOffset_nonexistent_returnedEmptyList_tests() throws Exception {
        return Arrays.asList(
                DynamicTest.dynamicTest(
                        "findAllLimitOffset_nonexistent_returnedEmptyList for Jockey",
                        () -> findAllLimitOffset_nonexistent_returnedEmptyList(unitOfWork.getJockeyDao())
                ),
                DynamicTest.dynamicTest(
                        "findAllLimitOffset_nonexistent_returnedEmptyList for Trainer",
                        () -> findAllLimitOffset_nonexistent_returnedEmptyList(unitOfWork.getTrainerDao())
                ),
                DynamicTest.dynamicTest(
                        "findAllLimitOffset_nonexistent_returnedEmptyList for Owner",
                        () -> findAllLimitOffset_nonexistent_returnedEmptyList(unitOfWork.getOwnerDao())
                )
        );
    }

    @TestFactory
    List<DynamicTest> findAll_tests() throws Exception {
        return Arrays.asList(
                DynamicTest.dynamicTest(
                        "findAll for Jockey",
                        () -> findAll(unitOfWork.getJockeyDao(), Jockey.builder())
                ),
                DynamicTest.dynamicTest(
                        "findAll for Trainer",
                        () -> findAll(unitOfWork.getTrainerDao(), Trainer.builder())
                ),
                DynamicTest.dynamicTest(
                        "findAll for Owner",
                        () -> findAll(unitOfWork.getOwnerDao(), Owner.builder())
                )
        );
    }

    @TestFactory
    List<DynamicTest> count_tests() throws Exception {
        return Arrays.asList(
                DynamicTest.dynamicTest(
                        "count for Jockey",
                        () -> count(unitOfWork.getJockeyDao())
                ),
                DynamicTest.dynamicTest(
                        "count for Trainer",
                        () -> count(unitOfWork.getTrainerDao())
                ),
                DynamicTest.dynamicTest(
                        "count for Owner",
                        () -> count(unitOfWork.getOwnerDao())
                )
        );
    }

    @TestFactory
    List<DynamicTest> findByNamePart_existent_returnedList_tests() throws Exception {
        return Arrays.asList(
                DynamicTest.dynamicTest(
                        "findByNamePart_existent_returnedList for Jockey",
                        () -> findByNamePart_existent_returnedList(unitOfWork.getJockeyDao(), Jockey.builder())
                ),
                DynamicTest.dynamicTest(
                        "findByNamePart_existent_returnedList for Trainer",
                        () -> findByNamePart_existent_returnedList(unitOfWork.getTrainerDao(), Trainer.builder())
                ),
                DynamicTest.dynamicTest(
                        "findByNamePart_existent_returnedList for Owner",
                        () -> findByNamePart_existent_returnedList(unitOfWork.getOwnerDao(), Owner.builder())
                )
        );
    }

    @TestFactory
    List<DynamicTest> findByNamePart_nonexistent_returnedEmptyList_tests() throws Exception {
        return Arrays.asList(
                DynamicTest.dynamicTest(
                        "findByNamePart_nonexistent_returnedEmptyList for Jockey",
                        () -> findByNamePart_nonexistent_returnedEmptyList(unitOfWork.getJockeyDao())
                ),
                DynamicTest.dynamicTest(
                        "findByNamePart_nonexistent_returnedEmptyList for Trainer",
                        () -> findByNamePart_nonexistent_returnedEmptyList(unitOfWork.getTrainerDao())
                ),
                DynamicTest.dynamicTest(
                        "findByNamePart_nonexistent_returnedEmptyList for Owner",
                        () -> findByNamePart_nonexistent_returnedEmptyList(unitOfWork.getOwnerDao())
                )
        );
    }

    @TestFactory
    List<DynamicTest> findByNamePartCount_tests() throws Exception {
        return Arrays.asList(
                DynamicTest.dynamicTest(
                        "findByNamePartCount for Jockey",
                        () -> findByNamePartCount(unitOfWork.getJockeyDao())
                ),
                DynamicTest.dynamicTest(
                        "findByNamePartCount for Trainer",
                        () -> findByNamePartCount(unitOfWork.getTrainerDao())
                ),
                DynamicTest.dynamicTest(
                        "findByNamePartCount for Owner",
                        () -> findByNamePartCount(unitOfWork.getOwnerDao())
                )
        );
    }

    @TestFactory
    List<DynamicTest> createAndDelete_tests() throws Exception {
        return Arrays.asList(
                DynamicTest.dynamicTest(
                        "createAndDelete for Jockey",
                        () -> createAndDelete(unitOfWork.getJockeyDao(), Jockey.builder())
                ),
                DynamicTest.dynamicTest(
                        "createAndDelete for Trainer",
                        () -> createAndDelete(unitOfWork.getTrainerDao(), Trainer.builder())
                ),
                DynamicTest.dynamicTest(
                        "createAndDelete for Owner",
                        () -> createAndDelete(unitOfWork.getOwnerDao(), Owner.builder())
                )
        );
    }

    @TestFactory
    List<DynamicTest> update_tests() throws Exception {
        return Arrays.asList(
                DynamicTest.dynamicTest(
                        "update for Jockey",
                        () -> update(unitOfWork.getJockeyDao(), Jockey.builder())
                ),
                DynamicTest.dynamicTest(
                        "update for Trainer",
                        () -> update(unitOfWork.getTrainerDao(), Trainer.builder())
                ),
                DynamicTest.dynamicTest(
                        "update for Owner",
                        () -> update(unitOfWork.getOwnerDao(), Owner.builder())
                )
        );
    }

    private <T extends Person> void find_existent_returnedPerson(PersonDao<T> personDao, PersonBuilder<T> builder)
            throws DalException, ParseException {
        T expectedResult = builder
                .setId(1)
                .setFirstName("Ruby")
                .setSecondName("Nichols")
                .setBirthday(sqlDateFromString("1982-04-21"))
                .build();

        T result = personDao.find(1L);

        assert result.equals(expectedResult) : "result != expectedResult";

    }

    private <T extends Person> void find_nonexistent_returnedNull(PersonDao<T> personDao) throws DalException {
        T result = personDao.find(300L);

        assert result == null : "result != null";

    }

    private <T extends Person> void findAllLimitOffset(PersonDao<T> personDao, PersonBuilder<T> builder)
            throws DalException, ParseException {

        List<T> expectedResult = new LinkedList<>();

        expectedResult.add(
                builder
                        .setId(1)
                        .setFirstName("Ruby")
                        .setSecondName("Nichols")
                        .setBirthday(sqlDateFromString("1982-04-21"))
                        .build()
        );
        expectedResult.add(
                builder
                        .setId(2)
                        .setFirstName("Nichols")
                        .setSecondName("Ruby")
                        .setBirthday(sqlDateFromString("1962-05-19"))
                        .build()
        );

        List<T> result = personDao.findAll(0, 2);

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    private <T extends Person> void findAllLimitOffset_nonexistent_returnedEmptyList(PersonDao<T> personDao) throws DalException {
        List<T> result = personDao.findAll(300, 400);

        assert result.isEmpty() : "result is not empty";
    }

    private <T extends Person> void findAll(PersonDao<T> personDao, PersonBuilder<T> builder)
            throws DalException, ParseException {

        List<T> expectedResult = new LinkedList<>();

        expectedResult.add(
                builder
                        .setId(1)
                        .setFirstName("Ruby")
                        .setSecondName("Nichols")
                        .setBirthday(sqlDateFromString("1982-04-21"))
                        .build()
        );
        expectedResult.add(
                builder
                        .setId(2)
                        .setFirstName("Nichols")
                        .setSecondName("Ruby")
                        .setBirthday(sqlDateFromString("1962-05-19"))
                        .build()
        );
        expectedResult.add(
                builder
                        .setId(3)
                        .setFirstName("Doris")
                        .setSecondName("Franklin")
                        .setBirthday(sqlDateFromString("1984-03-16"))
                        .build()
        );
        expectedResult.add(
                builder
                        .setId(4)
                        .setFirstName("Thomas")
                        .setSecondName("West")
                        .setBirthday(sqlDateFromString("1980-01-19"))
                        .build()
        );
        expectedResult.add(
                builder
                        .setId(5)
                        .setFirstName("Alex")
                        .setSecondName("Strutynski")
                        .setBirthday(sqlDateFromString("1980-04-21"))
                        .build()
        );

        List<T> result = personDao.findAll();

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    private <T extends Person> void count(PersonDao<T> personDao) throws DalException {
        long expectedResult = 5;

        long result = personDao.count();

        assert expectedResult == result : "result != expectedResult";
    }

    private <T extends Person> void findByNamePart_existent_returnedList(PersonDao<T> personDao, PersonBuilder<T> builder)
            throws DalException, ParseException {

        List<T> expectedResult1 = new LinkedList<>();
        List<T> expectedResult2 = new LinkedList<>();

        expectedResult1.add(
                builder
                        .setId(1)
                        .setFirstName("Ruby")
                        .setSecondName("Nichols")
                        .setBirthday(sqlDateFromString("1982-04-21"))
                        .build()
        );
        expectedResult2.add(
                builder
                        .setId(2)
                        .setFirstName("Nichols")
                        .setSecondName("Ruby")
                        .setBirthday(sqlDateFromString("1962-05-19"))
                        .build()
        );

        List<T> result1 = personDao.findByNamePart("Ru", 0, 1);
        List<T> result2 = personDao.findByNamePart("Ru", 1, 1);

        assert result1.equals(expectedResult1) : "result1 != expectedResult1";
        assert result2.equals(expectedResult2) : "result2 != expectedResult2";
    }

    private <T extends Person> void findByNamePart_nonexistent_returnedEmptyList(PersonDao<T> personDao) throws DalException {
        List<T> result = personDao.findByNamePart("bla-bla-bla", 0, 300);

        assert result.isEmpty() : "result is not empty";
    }

    private <T extends Person> void findByNamePartCount(PersonDao<T> personDao) throws Exception {
        long expectedResult = 2;

        long result = personDao.findByNamePartCount("Ru");

        assert expectedResult == result : "result != expectedResult";
    }

    private <T extends Person> void createAndDelete(PersonDao<T> personDao, PersonBuilder<T> builder)
            throws DalException, ParseException {

        T newPerson = builder
                .setFirstName("Alexander")
                .setSecondName("Barchenko")
                .setBirthday(sqlDateFromString("1996-11-30"))
                .build();

        personDao.create(newPerson);

        T person1 = personDao.find(newPerson.getId());

        assert newPerson.equals(person1) : "Dao did not create Person";

        personDao.delete(newPerson.getId());

        T person2 = personDao.find(newPerson.getId());

        assert person2 == null : "Dao did not delete Person";
    }

    private <T extends Person> void update(PersonDao<T> personDao, PersonBuilder<T> builder)
            throws DalException, ParseException {

        final long targetId = 5L;

        T person = personDao.find(targetId);
        T updated = builder
                .setId(person.getId())
                .setFirstName("Matthew")
                .setSecondName("Taylor")
                .setBirthday(sqlDateFromString("1995-01-15"))
                .build();

        assert !updated.equals(person) : "person and updated already same";

        personDao.update(updated);

        T afterSave = personDao.find(targetId);

        assert updated.equals(afterSave) : "updated != afterSave";

        // rollback

        personDao.update(person);

    }
}