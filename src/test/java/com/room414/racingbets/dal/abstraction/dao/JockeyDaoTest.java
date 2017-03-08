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
    void find_existent_returnedPerson() throws Exception {
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
    void find_nonexistent_returnedNull() throws Exception {
        try (UnitOfWork unitOfWork = unitOfWorkFactory.create()) {
            JockeyDao jockeyDao = unitOfWork.getJockeyDao();

            Jockey jockey = jockeyDao.find(300L);

            assert jockey == null : "jockey != null";
        }
    }

    @Test
    void findAllLimitOffset() throws Exception {
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

            List<Jockey> jockeys = jockeyDao.findAll(0, 2);

            assert jockeys.equals(expectedResult) : "jockeys != expectedResult";
        }
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
    void count() throws Exception {
        try (UnitOfWork unitOfWork = unitOfWorkFactory.create()) {
            JockeyDao jockeyDao = unitOfWork.getJockeyDao();
            long expectedResult = 5;

            long count = jockeyDao.count();

            assert expectedResult == count : "count != expectedResult";
        }
    }

    @Test
    void findByNamePart_existent_returnedList() throws Exception {
        try (UnitOfWork unitOfWork = unitOfWorkFactory.create()) {
            JockeyDao jockeyDao = unitOfWork.getJockeyDao();
            List<Jockey> expectedResult1 = new LinkedList<>();
            List<Jockey> expectedResult2 = new LinkedList<>();

            expectedResult1.add(
                    Jockey.builder()
                            .setId(1)
                            .setFirstName("Ruby")
                            .setSecondName("Nichols")
                            .setBirthday(sqlDateFromString("1982-04-21"))
                            .build()
            );
            expectedResult2.add(
                    Jockey.builder()
                            .setId(2)
                            .setFirstName("Nichols")
                            .setSecondName("Ruby")
                            .setBirthday(sqlDateFromString("1962-05-19"))
                            .build()
            );

            List<Jockey> jockeysFirstSet = jockeyDao.findByNamePart("Ru", 0, 1);
            List<Jockey> jockeysSecondSet = jockeyDao.findByNamePart("Ru", 1, 1);

            assert jockeysFirstSet.equals(expectedResult1) : "jockeysFirstSet != expectedResult1";
            assert jockeysSecondSet.equals(expectedResult2) : "jockeysSecondSet != expectedResult2";
        }
    }

    @Test
    void findByNamePart_nonexistent_returnedEmptyList() throws Exception {
        try (UnitOfWork unitOfWork = unitOfWorkFactory.create()) {
            JockeyDao jockeyDao = unitOfWork.getJockeyDao();

            List<Jockey> result = jockeyDao.findByNamePart("bla-bla-bla", 0, 300);

            assert result.isEmpty() : "result is not empty";
        }
    }

    @Test
    void findByNamePartCount() throws Exception {
        try (UnitOfWork unitOfWork = unitOfWorkFactory.create()) {
            JockeyDao jockeyDao = unitOfWork.getJockeyDao();
            long expectedResult = 2;

            long count = jockeyDao.findByNamePartCount("Ru");

            assert expectedResult == count : "count != expectedResult";
        }
    }

    @Test
    void createAndDelete() throws Exception {
        try (UnitOfWork unitOfWork = unitOfWorkFactory.create()) {
            JockeyDao jockeyDao = unitOfWork.getJockeyDao();
            Jockey newPerson = Jockey.builder()
                    .setFirstName("Alexander")
                    .setSecondName("Barchenko")
                    .setBirthday(sqlDateFromString("1996-11-30"))
                    .build();

            jockeyDao.create(newPerson);

            Jockey person1 = jockeyDao.find(newPerson.getId());

            assert newPerson.equals(person1) : "Dao did not create Person";

            jockeyDao.delete(newPerson.getId());

            Jockey person2 = jockeyDao.find(newPerson.getId());

            assert person2 == null : "Dao did not delete Person";
        }
    }

    @Test
    void update() throws Exception {
        try (UnitOfWork unitOfWork = unitOfWorkFactory.create()) {
            final long targetId = 5L;

            JockeyDao jockeyDao = unitOfWork.getJockeyDao();
            Jockey jockey = jockeyDao.find(targetId);
            Jockey updated = Jockey.builder()
                    .setId(jockey.getId())
                    .setFirstName("Matthew")
                    .setSecondName("Taylor")
                    .setBirthday(sqlDateFromString("1995-01-15"))
                    .build();

            assert !updated.equals(jockey) : "jockey and updated already same";

            jockeyDao.update(updated);

            Jockey afterSave = jockeyDao.find(targetId);

            assert updated.equals(afterSave) : "updated != afterSave";

            // rollback

            jockeyDao.update(jockey);
        }
    }
}