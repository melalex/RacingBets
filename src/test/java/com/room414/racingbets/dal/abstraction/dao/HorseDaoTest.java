package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Owner;
import com.room414.racingbets.dal.domain.entities.Trainer;
import com.room414.racingbets.dal.domain.proxies.HorseLazyLoadProxy;
import com.room414.racingbets.dal.resolvers.UnitOfWorkParameterResolver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import static com.room414.racingbets.dal.infrastructure.TestHelper.sqlDateFromString;

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
    void find_damAndSireNull_returnedEntity() throws ParseException, DalException {
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

        Owner owner = Owner.builder()
                .setId(3)
                .setFirstName("Doris")
                .setSecondName("Franklin")
                .setBirthday(sqlDateFromString("1984-03-16"))
                .build();

        Trainer trainer = Trainer.builder()
                .setId(3)
                .setFirstName("Doris")
                .setSecondName("Franklin")
                .setBirthday(sqlDateFromString("1984-03-16"))
                .build();

        Horse expectedResult = Horse.builder()
                .setId(3)
                .setName("Prodder")
                .setBirthday(sqlDateFromString("2011-12-26"))
                .setGender("mare")
                .setTrainer(trainer)
                .setOwner(owner)
                .setSir(HorseLazyLoadProxy.create(2))
                .setDam(HorseLazyLoadProxy.create(1))
                .build();

        Horse result = horseDao.find(3L);

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void find_nonexistent_returnedNull() throws DalException {
        HorseDao horseDao = getHorseDao();
        Horse result = horseDao.find(300L);

        assert result == null : "result != null";
    }

    @Test
    void findAllLimitOffset() throws ParseException, DalException {
        HorseDao horseDao = getHorseDao();

        Owner owner1 = Owner.builder()
                .setId(1)
                .setFirstName("Ruby")
                .setSecondName("Nichols")
                .setBirthday(sqlDateFromString("1982-04-21"))
                .build();

        Trainer trainer1 = Trainer.builder()
                .setId(5)
                .setFirstName("Alex")
                .setSecondName("Strutynski")
                .setBirthday(sqlDateFromString("1980-04-21"))
                .build();

        Owner owner2 = Owner.builder()
                .setId(2)
                .setFirstName("Nichols")
                .setSecondName("Ruby")
                .setBirthday(sqlDateFromString("1962-05-19"))
                .build();

        Trainer trainer2 = Trainer.builder()
                .setId(4)
                .setFirstName("Thomas")
                .setSecondName("West")
                .setBirthday(sqlDateFromString("1980-01-19"))
                .build();


        List<Horse> expectedResult = new LinkedList<>();

        expectedResult.add(Horse.builder()
                .setId(1)
                .setName("Fixflex")
                .setBirthday(sqlDateFromString("2008-02-22"))
                .setGender("mare")
                .setTrainer(trainer1)
                .setOwner(owner1)
                .build()
        );

        expectedResult.add(Horse.builder()
                .setId(2)
                .setName("Wrapsafe")
                .setBirthday(sqlDateFromString("2005-08-04"))
                .setGender("stallion")
                .setTrainer(trainer2)
                .setOwner(owner2)
                .build()
        );

        List<Horse> result = horseDao.findAll(0, 2);

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void findAllLimitOffset_nonexistent_returnedEmptyList() throws DalException {
        HorseDao horseDao = getHorseDao();
        List<Horse> result = horseDao.findAll(300L, 400L);

        assert result.isEmpty() : "result is not empty";
    }

    @Test
    void findAll() throws ParseException, DalException {
        Trainer trainer1 = Trainer.builder()
                .setId(1)
                .setFirstName("Ruby")
                .setSecondName("Nichols")
                .setBirthday(sqlDateFromString("1982-04-21"))
                .build();

        Trainer trainer2 = Trainer.builder()
                .setId(2)
                .setFirstName("Nichols")
                .setSecondName("Ruby")
                .setBirthday(sqlDateFromString("1962-05-19"))
                .build();

        Trainer trainer3 = Trainer.builder()
                .setId(3)
                .setFirstName("Doris")
                .setSecondName("Franklin")
                .setBirthday(sqlDateFromString("1984-03-16"))
                .build();

        Trainer trainer4 = Trainer.builder()
                .setId(4)
                .setFirstName("Thomas")
                .setSecondName("West")
                .setBirthday(sqlDateFromString("1980-01-19"))
                .build();

        Trainer trainer5 = Trainer.builder()
                .setId(5)
                .setFirstName("Alex")
                .setSecondName("Strutynski")
                .setBirthday(sqlDateFromString("1980-04-21"))
                .build();

        Owner owner1 = Owner.builder()
                .setId(1)
                .setFirstName("Ruby")
                .setSecondName("Nichols")
                .setBirthday(sqlDateFromString("1982-04-21"))
                .build();

        Owner owner2 = Owner.builder()
                .setId(2)
                .setFirstName("Nichols")
                .setSecondName("Ruby")
                .setBirthday(sqlDateFromString("1962-05-19"))
                .build();

        Owner owner3 = Owner.builder()
                .setId(3)
                .setFirstName("Doris")
                .setSecondName("Franklin")
                .setBirthday(sqlDateFromString("1984-03-16"))
                .build();

        Owner owner4 = Owner.builder()
                .setId(4)
                .setFirstName("Thomas")
                .setSecondName("West")
                .setBirthday(sqlDateFromString("1980-01-19"))
                .build();

        Owner owner5 = Owner.builder()
                .setId(5)
                .setFirstName("Alex")
                .setSecondName("Strutynski")
                .setBirthday(sqlDateFromString("1980-04-21"))
                .build();

        List<Horse> expectedResult = new LinkedList<>();

        expectedResult.add(Horse.builder()
                .setId(1)
                .setName("Fixflex")
                .setBirthday(sqlDateFromString("2008-02-22"))
                .setGender("mare")
                .setTrainer(trainer5)
                .setOwner(owner1)
                .build()
        );

        expectedResult.add(Horse.builder()
                .setId(2)
                .setName("Wrapsafe")
                .setBirthday(sqlDateFromString("2005-08-04"))
                .setGender("stallion")
                .setTrainer(trainer4)
                .setOwner(owner2)
                .build()
        );

        expectedResult.add(Horse.builder()
                .setId(3)
                .setName("Prodder")
                .setBirthday(sqlDateFromString("2011-12-26"))
                .setGender("mare")
                .setTrainer(trainer3)
                .setOwner(owner3)
                .setSir(HorseLazyLoadProxy.create(2))
                .setDam(HorseLazyLoadProxy.create(1))
                .build()
        );

        expectedResult.add(Horse.builder()
                .setId(4)
                .setName("Span")
                .setBirthday(sqlDateFromString("2011-03-27"))
                .setGender("stallion")
                .setTrainer(trainer2)
                .setOwner(owner4)
                .setSir(HorseLazyLoadProxy.create(2))
                .setDam(HorseLazyLoadProxy.create(1))
                .build()
        );

        expectedResult.add(Horse.builder()
                .setId(5)
                .setName("Treeflex")
                .setBirthday(sqlDateFromString("2013-05-01"))
                .setGender("mare")
                .setTrainer(trainer1)
                .setOwner(owner5)
                .setSir(HorseLazyLoadProxy.create(3))
                .setDam(HorseLazyLoadProxy.create(4))
                .build()
        );

        expectedResult.add(Horse.builder()
                .setId(6)
                .setName("Alphazap")
                .setBirthday(sqlDateFromString("2000-10-25"))
                .setGender("stallion")
                .setTrainer(trainer4)
                .setOwner(owner2)
                .build()
        );

        expectedResult.add(Horse.builder()
                .setId(7)
                .setName("Aerified")
                .setBirthday(sqlDateFromString("2010-11-27"))
                .setGender("stallion")
                .setTrainer(trainer2)
                .setOwner(owner4)
                .setSir(HorseLazyLoadProxy.create(2))
                .setDam(HorseLazyLoadProxy.create(1))
                .build()
        );

        expectedResult.add(Horse.builder()
                .setId(8)
                .setName("Prob")
                .setBirthday(sqlDateFromString("2014-05-01"))
                .setGender("mare")
                .setTrainer(trainer3)
                .setOwner(owner3)
                .setSir(HorseLazyLoadProxy.create(3))
                .setDam(HorseLazyLoadProxy.create(4))
                .build()
        );

        List<Horse> result = getHorseDao().findAll();

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void count() throws DalException {
        long expectedResult = 8;

        long result = getHorseDao().count();

        assert expectedResult == result : "result != expectedResult";

    }

    @Test
    void findByNamePart_existent_returnedList() throws ParseException, DalException {
        HorseDao horseDao = getHorseDao();

        Trainer trainer3 = Trainer.builder()
                .setId(3)
                .setFirstName("Doris")
                .setSecondName("Franklin")
                .setBirthday(sqlDateFromString("1984-03-16"))
                .build();

        Owner owner3 = Owner.builder()
                .setId(3)
                .setFirstName("Doris")
                .setSecondName("Franklin")
                .setBirthday(sqlDateFromString("1984-03-16"))
                .build();

        List<Horse> expectedResult1 = new LinkedList<>();
        List<Horse> expectedResult2 = new LinkedList<>();

        expectedResult1.add(Horse.builder()
                .setId(3)
                .setName("Prodder")
                .setBirthday(sqlDateFromString("2011-12-26"))
                .setGender("mare")
                .setTrainer(trainer3)
                .setOwner(owner3)
                .setSir(HorseLazyLoadProxy.create(2))
                .setDam(HorseLazyLoadProxy.create(1))
                .build()
        );

        expectedResult2.add(Horse.builder()
                .setId(8)
                .setName("Prob")
                .setBirthday(sqlDateFromString("2014-05-01"))
                .setGender("mare")
                .setTrainer(trainer3)
                .setOwner(owner3)
                .setSir(HorseLazyLoadProxy.create(3))
                .setDam(HorseLazyLoadProxy.create(4))
                .build()
        );

        List<Horse> result1 = horseDao.findByNamePart("Pro", 0, 1);
        List<Horse> result2 = horseDao.findByNamePart("Pro", 1, 1);

        assert result1.equals(expectedResult1) : "result1 != expectedResult1";
        assert result2.equals(expectedResult2) : "result2 != expectedResult2";
    }

    @Test
    void findByNamePart_nonexistent_returnedEmptyList() throws DalException {
        List<Horse> result = getHorseDao().findByNamePart("bla-bla-bla", 0L, 1L);

        assert result.isEmpty() : "result is not empty";

    }

    @Test
    void findByNamePartCount() throws DalException {
        long expectedResult = 2;

        long result = getHorseDao().findByNamePartCount("Pro");

        assert result == expectedResult : "result != expectedResult";

    }

    @Test
    void createAndDelete_nullSireAndDam_createdDeleted() throws DalException, ParseException {
        HorseDao horseDao = getHorseDao();

        Trainer trainer3 = Trainer.builder()
                .setId(3)
                .setFirstName("Doris")
                .setSecondName("Franklin")
                .setBirthday(sqlDateFromString("1984-03-16"))
                .build();

        Owner owner3 = Owner.builder()
                .setId(3)
                .setFirstName("Doris")
                .setSecondName("Franklin")
                .setBirthday(sqlDateFromString("1984-03-16"))
                .build();

        Horse newEntity = Horse.builder()
                .setName("Prodder")
                .setBirthday(sqlDateFromString("2011-12-26"))
                .setGender("mare")
                .setTrainer(trainer3)
                .setOwner(owner3)
                .build();

        horseDao.create(newEntity);

        Horse entity1 = horseDao.find(newEntity.getId());

        assert newEntity.equals(entity1) : "Dao did not createUnitOfWorkFactory Person";

        horseDao.delete(newEntity.getId());

        Horse entity2 = horseDao.find(newEntity.getId());

        assert entity2 == null : "Dao did not delete Person";
    }

    @Test
    void createAndDelete_notNullSireAndDam_createdDeleted() throws DalException, ParseException {
        HorseDao horseDao = getHorseDao();

        Trainer trainer3 = Trainer.builder()
                .setId(3)
                .setFirstName("Doris")
                .setSecondName("Franklin")
                .setBirthday(sqlDateFromString("1984-03-16"))
                .build();

        Owner owner3 = Owner.builder()
                .setId(3)
                .setFirstName("Doris")
                .setSecondName("Franklin")
                .setBirthday(sqlDateFromString("1984-03-16"))
                .build();

        Horse newEntity = Horse.builder()
                .setName("Prodder")
                .setBirthday(sqlDateFromString("2011-12-26"))
                .setGender("mare")
                .setTrainer(trainer3)
                .setOwner(owner3)
                .setSir(HorseLazyLoadProxy.create(3))
                .setDam(HorseLazyLoadProxy.create(4))
                .build();

        horseDao.create(newEntity);

        Horse entity1 = horseDao.find(newEntity.getId());

        assert newEntity.equals(entity1) : "Dao did not create Horse";

        horseDao.delete(newEntity.getId());

        Horse entity2 = horseDao.find(newEntity.getId());

        assert entity2 == null : "Dao did not delete Person";
    }

    @Test
    void update() throws DalException, ParseException {
        final long targetId = 6L;

        HorseDao horseDao = getHorseDao();

        Trainer trainer1 = Trainer.builder()
                .setId(1)
                .setFirstName("Ruby")
                .setSecondName("Nichols")
                .setBirthday(sqlDateFromString("1982-04-21"))
                .build();

        Owner owner2 = Owner.builder()
                .setId(2)
                .setFirstName("Nichols")
                .setSecondName("Ruby")
                .setBirthday(sqlDateFromString("1962-05-19"))
                .build();

        Horse entity = horseDao.find(targetId);
        Horse updated = Horse.builder()
                .setId(entity.getId())
                .setName("New Alphazap")
                .setBirthday(sqlDateFromString("1999-10-25"))
                .setGender("mare")
                .setTrainer(trainer1)
                .setOwner(owner2)
                .build();

        assert !updated.equals(entity) : "entity and updated are already same";

        horseDao.update(updated);

        Horse afterSave = horseDao.find(targetId);

        assert updated.equals(afterSave) : "updated != afterSave";

        // rollback

        horseDao.update(entity);
    }
}