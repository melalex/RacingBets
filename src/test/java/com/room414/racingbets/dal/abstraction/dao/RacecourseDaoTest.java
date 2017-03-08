package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Racecourse;
import com.room414.racingbets.dal.resolvers.UnitOfWorkParameterResolver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author melalex
 * @version 1.0 09 Mar 2017
 */
@ExtendWith(UnitOfWorkParameterResolver.class)
class RacecourseDaoTest {
    private static UnitOfWork unitOfWork;

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
    void find_existent_returnedEntity() throws ParseException, DalException {
        RacecourseDao dao = getRacecourseDao();

        Racecourse expectedResult = Racecourse.builder()
                .setId(1)
                .setName("Ronstring")
                .setLatitude(-22.72528)
                .setLongitude(-47.64917)
                .setClerk("Stephen Cook")
                .setContact("scook0@hud.gov")
                .build();

        Racecourse result = dao.find(1L);

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void find_nonexistent_returnedNull() throws DalException {
        RacecourseDao dao = getRacecourseDao();

        Racecourse result = dao.find(300L);

        assert result == null : "result != null";
    }

    @Test
    void findAllLimitOffset() throws ParseException, DalException {
        RacecourseDao dao = getRacecourseDao();
        List<Racecourse> expectedResult = new LinkedList<>();

        expectedResult.add(Racecourse.builder()
                .setId(1)
                .setName("Ronstring")
                .setLatitude(-22.72528)
                .setLongitude(-47.64917)
                .setClerk("Stephen Cook")
                .setContact("scook0@hud.gov")
                .build()
        );


        List<Racecourse> result = dao.findAll(0, 1);

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void findAllLimitOffset_nonexistent_returnedEmptyList() throws DalException {
        RacecourseDao dao = getRacecourseDao();

        List<Racecourse> result = dao.findAll(300L, 400L);

        assert result.isEmpty() : "result is not empty";
    }

    @Test
    void findAll() throws ParseException, DalException {
        RacecourseDao dao = getRacecourseDao();

        List<Racecourse> expectedResult = new LinkedList<>();

        expectedResult.add(Racecourse.builder()
                .setId(1)
                .setName("Ronstring")
                .setLatitude(-22.72528)
                .setLongitude(-47.64917)
                .setContact("scook0@hud.gov")
                .setClerk("Stephen Cook")
                .build()
        );

        expectedResult.add(Racecourse.builder()
                .setId(2)
                .setName("Fintone")
                .setLatitude(29.95033)
                .setLongitude(121.74293)
                .setContact("ncunningham1@merriam-webster.com")
                .setClerk("Nicole Cunningham")
                .build()
        );

        expectedResult.add(Racecourse.builder()
                .setId(3)
                .setName("Flowdesk")
                .setLatitude(-20.26889)
                .setLongitude(-50.54583)
                .setContact("ajames2@amazon.co.jp")
                .setClerk("Annie James")
                .build()
        );

        List<Racecourse> result = dao.findAll();

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void count() throws DalException {
        RacecourseDao dao = getRacecourseDao();
        long expectedResult = 3;

        long result = dao.count();

        assert expectedResult == result : "result != expectedResult";

    }

    @Test
    void findByNamePart_existent_returnedList() throws ParseException, DalException {
        RacecourseDao dao = getRacecourseDao();

        List<Racecourse> expectedResult = new LinkedList<>();

        expectedResult.add(Racecourse.builder()
                .setId(1)
                .setName("Ronstring")
                .setLatitude(-22.72528)
                .setLongitude(-47.64917)
                .setContact("scook0@hud.gov")
                .setClerk("Stephen Cook")
                .build()
        );

        List<Racecourse> result = dao.findByNamePart("Ron", 0, 1);

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void findByNamePart_nonexistent_returnedEmptyList() throws DalException {
        RacecourseDao dao = getRacecourseDao();

        List<Racecourse> result = dao.findByNamePart("bla-bla-bla", 0L, 1L);

        assert result.isEmpty() : "result is not empty";

    }

    @Test
    void findByNamePartCount() throws DalException {
        RacecourseDao dao = getRacecourseDao();
        long expectedResult = 1;

        long result = dao.findByNamePartCount("Ron");

        assert result == expectedResult : "result != expectedResult";

    }

    @Test
    void createAndDelete() throws DalException, ParseException {
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
    void update() throws DalException, ParseException {
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

        assert updated.equals(afterSave) : "updated != afterSave";

        // rollback

        dao.update(entity);
    }

}