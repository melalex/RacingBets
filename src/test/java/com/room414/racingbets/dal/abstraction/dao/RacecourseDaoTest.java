package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.domain.entities.Racecourse;
import com.room414.racingbets.dal.infrastructure.EntityStorage;
import com.room414.racingbets.resolvers.UnitOfWorkParameterResolver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import static com.room414.racingbets.dal.infrastructure.TestHelper.defaultAssertionFailMessage;

/**
 * @author melalex
 * @version 1.0 09 Mar 2017
 */
@ExtendWith(UnitOfWorkParameterResolver.class)
class RacecourseDaoTest {
    private static UnitOfWork unitOfWork;

    private EntityStorage storage = EntityStorage.getInstance();

    @BeforeAll
    static void setUp(UnitOfWork unitOfWork) {
        RacecourseDaoTest.unitOfWork = unitOfWork;
    }

    @AfterAll
    static void tearDown() throws Exception {
        unitOfWork.close();
    }

    private static RacecourseDao getRacecourseDao() {
        return unitOfWork.getRacecourseDao();
    }

    @Test
    @Tag("read")
    void find_existent_returnedEntity() throws ParseException {
        RacecourseDao dao = getRacecourseDao();

        Racecourse expectedResult = storage.getRacecourse(1);

        Racecourse result = dao.find(1L);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void find_nonexistent_returnedNull() {
        RacecourseDao dao = getRacecourseDao();

        Racecourse result = dao.find(300L);

        assert result == null : defaultAssertionFailMessage(result, null);
    }

    @Test
    @Tag("read")
    void findAllLimitOffset() throws ParseException {
        RacecourseDao dao = getRacecourseDao();
        List<Racecourse> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getRacecourse(1));

        List<Racecourse> result = dao.findAll(0, 1);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void findAllLimitOffset_nonexistent_returnedEmptyList() {
        RacecourseDao dao = getRacecourseDao();

        List<Racecourse> result = dao.findAll(300, 400);

        assert result.isEmpty() : "result is not empty";
    }

    @Test
    @Tag("read")
    void findAll() throws ParseException {
        RacecourseDao dao = getRacecourseDao();

        List<Racecourse> expectedResult = storage.getAllRacecourses();

        List<Racecourse> result = dao.findAll();

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void count() {
        RacecourseDao dao = getRacecourseDao();
        long expectedResult = 3;

        long result = dao.count();

        assert expectedResult == result : defaultAssertionFailMessage(result, expectedResult);

    }

    @Test
    @Tag("read")
    void findByNamePart_existent_returnedList() throws ParseException {
        RacecourseDao dao = getRacecourseDao();

        List<Racecourse> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getRacecourse(1));

        List<Racecourse> result = dao.search("Ron", 0, 1);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void findByNamePart_nonexistent_returnedEmptyList() {
        RacecourseDao dao = getRacecourseDao();

        List<Racecourse> result = dao.search("bla-bla-bla", 0, 1);

        assert result.isEmpty() : "result is not empty";

    }

    @Test
    @Tag("read")
    void findByNamePartCount() {
        RacecourseDao dao = getRacecourseDao();
        long expectedResult = 1;

        long result = dao.searchCount("Ron");

        assert result == expectedResult : defaultAssertionFailMessage(result, expectedResult);

    }

    @Test
    @Tag("write")
    void createAndDelete() throws ParseException {
        RacecourseDao dao = getRacecourseDao();

        Racecourse newEntity = Racecourse.builder()
                .setName("Stronghold")
                .setLatitude(14.64361)
                .setLongitude(120.58083)
                .setContact("jandrews6@spiegel.de")
                .setClerk("Joyce Andrews")
                .build();

        dao.create(newEntity);

        Racecourse entity1 = dao.find(newEntity.getId());

        assert newEntity.equals(entity1) : "Dao did not create Entity";

        dao.delete(newEntity.getId());

        Racecourse entity2 = dao.find(newEntity.getId());

        assert entity2 == null : "Dao did not delete Entity";
    }

    @Test
    @Tag("write")
    void update() throws ParseException {
        final long targetId = 3L;

        RacecourseDao dao = getRacecourseDao();

        Racecourse entity = dao.find(targetId);
        Racecourse updated = Racecourse.builder()
                .setId(entity.getId())
                .setName("Stim")
                .setLatitude(49.38858)
                .setLongitude(16.1096)
                .setContact("gsnyder1a@aboutads.info")
                .setClerk("Gary Snyder")
                .build();

        assert !updated.equals(entity) : "entity and updated are already same";

        dao.update(updated);

        Racecourse afterSave = dao.find(targetId);

        assert updated.equals(afterSave) : defaultAssertionFailMessage(afterSave, updated);

        // rollback

        dao.update(entity);
    }

}