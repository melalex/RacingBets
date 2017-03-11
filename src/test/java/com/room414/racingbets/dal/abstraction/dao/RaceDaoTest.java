package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.RaceStatus;
import com.room414.racingbets.dal.domain.enums.RaceType;
import com.room414.racingbets.dal.domain.enums.TrackCondition;
import com.room414.racingbets.dal.infrastructure.EntityStorage;
import com.room414.racingbets.dal.resolvers.UnitOfWorkParameterResolver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;


/**
 * @author melalex
 * @version 1.0 09 Mar 2017
 */
@ExtendWith(UnitOfWorkParameterResolver.class)
class RaceDaoTest {
    private static UnitOfWork unitOfWork;

    private EntityStorage storage = EntityStorage.getInstance();

    @BeforeAll
    static void setUp(UnitOfWork unitOfWork) {
        RaceDaoTest.unitOfWork = unitOfWork;
    }

    @AfterAll
    static void tearDown() throws Exception {
        unitOfWork.close();
    }

    private static RaceDao getDao() {
        return unitOfWork.getRaceDao();
    }

    @Test
    void find_existent_returnedEntity() throws DalException {
        RaceDao dao = getDao();

        Race expectedResult = storage.getRace(1);

        Race result = dao.find(1L);

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void find_withNull_returnedEntity() throws DalException {
        RaceDao dao = getDao();

        Race expectedResult = storage.getRace(3);

        Race result = dao.find(3L);

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void find_nonexistent_returnedNull() throws DalException {
        RaceDao dao = getDao();

        Race result = dao.find(300L);

        assert result == null : "result != null";
    }

    @Test
    void findAllLimitOffset() throws ParseException, DalException {
        RaceDao dao = getDao();
        List<Race> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getRace(1));
        expectedResult.add(storage.getRace(2));

        List<Race> result = dao.findAll(0, 2);

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void findAllLimitOffset_nonexistent_returnedEmptyList() throws DalException {
        RaceDao dao = getDao();

        List<Race> result = dao.findAll(300L, 400L);

        assert result.isEmpty() : "result is not empty";
    }

    @Test
    void findAll() throws ParseException, DalException {
        List<Race> expectedResult = storage.getAllRaces();

        List<Race> result = getDao().findAll();

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void count() throws DalException {
        RaceDao dao = getDao();
        long expectedResult = 3;

        long result = dao.count();

        assert expectedResult == result : "result != expectedResult";

    }

    @Test
    void findByRacecourseId() throws DalException {
        RaceDao dao = getDao();

        List<Race> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getRace(1));

        List<Race> result = dao.findByRacecourseId(RaceStatus.FINISHED,  1, 0, 1);

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void findByRacecourseIdCount() {
        RaceDao dao = getDao();
        long expectedResult = 1;

        long result = dao.findByRacecourseIdCount(RaceStatus.FINISHED, 1);

        assert expectedResult == result : "result != expectedResult";
    }

    @Test
    void findByRacecourse() throws DalException {
        RaceDao dao = getDao();

        List<Race> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getRace(1));

        List<Race> result = dao.findByRacecourse(RaceStatus.FINISHED,  "Ron", 0, 1);

        assert result.equals(expectedResult) : "result != expectedResult";

    }

    @Test
    void findByRacecourseCount() {
        RaceDao dao = getDao();
        long expectedResult = 1;

        long result = dao.findByRacecourseCount(RaceStatus.FINISHED, "Ron");

        assert expectedResult == result : "result != expectedResult";
    }

    @Test
    void findInTimestampDiapason() {
        RaceDao dao = getDao();

        List<Race> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getRace(1));

        List<Race> result = dao.findInTimestampDiapason(
                RaceStatus.FINISHED,
                Timestamp.valueOf("2017-03-08 00:00:00"),
                Timestamp.valueOf("2017-03-08 23:59:59"),
                0,
                1
        );

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void findInTimestampDiapasonCount() {
        RaceDao dao = getDao();
        long expectedResult = 1;

        long result = dao.findInTimestampDiapasonCount(
                RaceStatus.FINISHED,
                Timestamp.valueOf("2017-03-08 00:00:00"),
                Timestamp.valueOf("2017-03-08 23:59:59")
        );

        assert expectedResult == result : "result != expectedResult";
    }

    @Test
    void findInTimestampDiapasonOnRacecourse() {
        RaceDao dao = getDao();

        List<Race> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getRace(1));

        List<Race> result = dao.findInTimestampDiapasonOnRacecourse(
                RaceStatus.FINISHED,
                1,
                Timestamp.valueOf("2017-03-08 00:00:00"),
                Timestamp.valueOf("2017-03-08 23:59:59"),
                0,
                1
        );

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void findInTimestampDiapasonOnRacecourseCount() {
        RaceDao dao = getDao();
        long expectedResult = 1;

        long result = dao.findInTimestampDiapasonOnRacecourseCount(
                RaceStatus.FINISHED,
                1,
                Timestamp.valueOf("2017-03-08 00:00:00"),
                Timestamp.valueOf("2017-03-08 23:59:59")
        );

        assert expectedResult == result : "result != expectedResult";
    }

    @Test
    void findByNamePart() throws DalException {
        RaceDao dao = getDao();

        List<Race> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getRace(1));

        List<Race> result = dao.findByNamePart(RaceStatus.FINISHED,  "Gem", 0, 1);

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void findByNamePartCount() throws DalException {
        RaceDao dao = getDao();
        long expectedResult = 1;

        long result = dao.findByNamePartCount(RaceStatus.FINISHED, "Gem");

        assert expectedResult == result : "result != expectedResult";
    }

    @Test
    void createDelete() throws DalException, ParseException {
        RaceDao dao = getDao();

        Race newEntity = Race.builder()
                .setId(1)
                .setName("Asp")
                .setRaceStatus(RaceStatus.REJECTED)
                .setCommission(0.14)
                .setMinBet(BigDecimal.valueOf(5))
                .setRacecourse(storage.getRacecourse(2))
                .setStart(Timestamp.valueOf("2016-03-08 10:32:36"))
                .setTrackCondition(TrackCondition.GOOD_TO_SOFT)
                .setRaceType(RaceType.HARNESS)
                .setRaceClass(4)
                .setMinAge(2)
                .setMinRating(55)
                .setMaxRating(75)
                .setDistance(8.1f)
                .addParticipant(storage.getParticipant(1))
                .addParticipant(storage.getParticipant(2))
                .addParticipant(storage.getParticipant(3))
                .setPrize(1, BigDecimal.valueOf(300))
                .setPrize(2, BigDecimal.valueOf(300))
                .setPrize(3, BigDecimal.valueOf(300))
                .build();

        dao.create(newEntity);

        Race entity1 = dao.find(newEntity.getId());

        assert newEntity.equals(entity1) : "Dao did not create entity";

        dao.delete(newEntity.getId());

        Race entity2 = dao.find(newEntity.getId());

        assert entity2 == null : "Dao did not delete entity";
    }

    @Test
    void update() throws DalException, ParseException {
        final long targetId = 1L;

        RaceDao dao = getDao();

        Race entity = dao.find(targetId);
        Race updated = Race.builder()
                .setId(1)
                .setName("Asp")
                .setRaceStatus(RaceStatus.REJECTED)
                .setCommission(0.14)
                .setMinBet(BigDecimal.valueOf(5))
                .setRacecourse(storage.getRacecourse(2))
                .setStart(Timestamp.valueOf("2016-03-08 10:32:36"))
                .setTrackCondition(TrackCondition.GOOD_TO_SOFT)
                .setRaceType(RaceType.HARNESS)
                .setRaceClass(4)
                .setMinAge(2)
                .setMinRating(55)
                .setMaxRating(75)
                .setDistance(8.1f)
                .build();

        assert !updated.equals(entity) : "entity and updated are already same";

        dao.update(updated);

        Race afterSave = dao.find(entity.getId());

        assert updated.equals(afterSave) : "updated != afterSave";

        // rollback

        dao.update(entity);
    }


    @Test
    void updateStatus() throws DalException {
        final long targetId = 1L;
        RaceStatus newStatus = RaceStatus.REJECTED;
        RaceDao dao = getDao();

        Race entity = dao.find(targetId);

        assert entity.getRaceStatus() != newStatus : "entity' status and newStatus are already same";

        dao.updateStatus(entity.getId(), newStatus);

        Race afterSave = dao.find(entity.getId());

        assert afterSave.getRaceStatus() == newStatus : "updated != afterSave";

        // rollback

        dao.updateStatus(entity.getId(), entity.getRaceStatus());
    }
}