package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.domain.entities.Horse;
import com.room414.racingbets.dal.domain.entities.Owner;
import com.room414.racingbets.dal.domain.entities.Trainer;
import com.room414.racingbets.dal.infrastructure.EntityStorage;
import com.room414.racingbets.dal.resolvers.UnitOfWorkParameterResolver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import static com.room414.racingbets.dal.infrastructure.TestHelper.defaultAssertionFailMessage;
import static com.room414.racingbets.dal.infrastructure.TestHelper.sqlDateFromString;

/**
 * @author melalex
 * @version 1.0 08 Mar 2017
 */
@ExtendWith(UnitOfWorkParameterResolver.class)
class HorseDaoTest {
    private static UnitOfWork unitOfWork;

    private EntityStorage storage = EntityStorage.getInstance();

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
    @Tag("read")
    void find_damAndSireNull_returnedEntity() throws ParseException {
        HorseDao horseDao = getHorseDao();

        Horse expectedResult = storage.getHorse(1);

        Horse result = horseDao.find(1L);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void find_existent_returnedEntity() throws ParseException {
        HorseDao horseDao = getHorseDao();

        Horse expectedResult = storage.getHorse(3);

        Horse result = horseDao.find(3L);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void find_nonexistent_returnedNull() {
        HorseDao horseDao = getHorseDao();
        Horse result = horseDao.find(300L);

        assert result == null : defaultAssertionFailMessage(result, null);
    }

    @Test
    @Tag("read")
    void findAllLimitOffset() throws ParseException {
        HorseDao horseDao = getHorseDao();

        List<Horse> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getHorse(1));

        expectedResult.add(storage.getHorse(2));

        List<Horse> result = horseDao.findAll(0, 2);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void findAllLimitOffset_nonexistent_returnedEmptyList() {
        HorseDao horseDao = getHorseDao();
        List<Horse> result = horseDao.findAll(300, 400);

        assert result.isEmpty() : "result is not empty";
    }

    @Test
    @Tag("read")
    void findAll() throws ParseException {
        List<Horse> expectedResult = storage.getAllHorses();

        List<Horse> result = getHorseDao().findAll();

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void count() {
        long expectedResult = 8;

        long result = getHorseDao().count();

        assert expectedResult == result : defaultAssertionFailMessage(result, expectedResult);

    }

    @Test
    @Tag("read")
    void findByNamePart_existent_returnedList() throws ParseException {
        HorseDao horseDao = getHorseDao();

        List<Horse> expectedResult1 = new LinkedList<>();
        List<Horse> expectedResult2 = new LinkedList<>();

        expectedResult1.add(storage.getHorse(3));

        expectedResult2.add(storage.getHorse(8));

        List<Horse> result1 = horseDao.search("Pro", 0, 1);
        List<Horse> result2 = horseDao.search("Pro", 1, 1);

        assert result1.equals(expectedResult1) : defaultAssertionFailMessage(result1, expectedResult1);
        assert result2.equals(expectedResult2) : defaultAssertionFailMessage(result2, expectedResult2);
    }

    @Test
    @Tag("read")
    void findByNamePart_nonexistent_returnedEmptyList() {
        List<Horse> result = getHorseDao().search("bla-bla-bla", 0, 1);

        assert result.isEmpty() : "result is not empty";

    }

    @Test
    @Tag("read")
    void findByNamePartCount() {
        long expectedResult = 2;

        long result = getHorseDao().searchCount("Pro");

        assert result == expectedResult : defaultAssertionFailMessage(result, expectedResult);

    }

    @Test
    @Tag("write")
    void createAndDelete_nullSireAndDam_createdDeleted() throws ParseException {
        HorseDao horseDao = getHorseDao();

        Trainer trainer3 = storage.getTrainer(3);
        Owner owner3 = storage.getOwner(3);

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
    @Tag("write")
    void createAndDelete_notNullSireAndDam_createdDeleted() throws ParseException {
        HorseDao horseDao = getHorseDao();

        Trainer trainer3 = storage.getTrainer(3);
        Owner owner3 = storage.getOwner(3);

        Horse newEntity = Horse.builder()
                .setName("Prodder")
                .setBirthday(sqlDateFromString("2011-12-26"))
                .setGender("mare")
                .setTrainer(trainer3)
                .setOwner(owner3)
                .setSir(3)
                .setDam(4)
                .build();

        horseDao.create(newEntity);

        Horse entity1 = horseDao.find(newEntity.getId());

        assert newEntity.equals(entity1) : "Dao did not create Horse";

        horseDao.delete(newEntity.getId());

        Horse entity2 = horseDao.find(newEntity.getId());

        assert entity2 == null : "Dao did not delete Person";
    }

    @Test
    @Tag("write")
    void update() throws ParseException {
        final long targetId = 6L;

        HorseDao horseDao = getHorseDao();

        Trainer trainer1 = storage.getTrainer(1);

        Owner owner2 = storage.getOwner(2);

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

        assert updated.equals(afterSave) : defaultAssertionFailMessage(afterSave, updated);

        // rollback

        horseDao.update(entity);
    }
}