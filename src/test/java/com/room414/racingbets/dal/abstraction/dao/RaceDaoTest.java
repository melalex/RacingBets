package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.RaceStatus;
import com.room414.racingbets.dal.domain.enums.RaceType;
import com.room414.racingbets.dal.domain.enums.TrackCondition;
import com.room414.racingbets.dal.infrastructure.EntityStorage;
import com.room414.racingbets.dal.resolvers.UnitOfWorkParameterResolver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import static com.room414.racingbets.dal.infrastructure.TestHelper.defaultAssertionFailMessage;


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
    @Tag("read")
    void find_existent_returnedEntity() throws DalException {
        RaceDao dao = getDao();

        Race expectedResult = storage.getRace(1);

        Race result = dao.find(1L);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void find_withNull_returnedEntity() throws DalException {
        RaceDao dao = getDao();

        Race expectedResult = storage.getRace(3);

        Race result = dao.find(3L);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void find_nonexistent_returnedNull() throws DalException {
        RaceDao dao = getDao();

        Race result = dao.find(300L);

        assert result == null : defaultAssertionFailMessage(result, null);
    }

    @Test
    @Tag("read")
    void findAllLimitOffset() throws ParseException, DalException {
        RaceDao dao = getDao();
        List<Race> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getRace(1));
        expectedResult.add(storage.getRace(2));

        List<Race> result = dao.findAll(0, 2);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void findAllLimitOffset_nonexistent_returnedEmptyList() throws DalException {
        RaceDao dao = getDao();

        List<Race> result = dao.findAll(300L, 400L);

        assert result.isEmpty() : "result is not empty";
    }

    @Test
    @Tag("read")
    void findAll() throws ParseException, DalException {
        List<Race> expectedResult = storage.getAllRaces();

        List<Race> result = getDao().findAll();

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void count() throws DalException {
        RaceDao dao = getDao();
        long expectedResult = 3;

        long result = dao.count();

        assert expectedResult == result : defaultAssertionFailMessage(result, expectedResult);

    }

    @Test
    @Tag("read")
    void findByRacecourseId() throws DalException {
        RaceDao dao = getDao();

        List<Race> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getRace(1));

        List<Race> result = dao.findByRacecourseId(RaceStatus.FINISHED,  1, 0, 1);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void findByRacecourseIdCount() throws DalException {
        RaceDao dao = getDao();
        long expectedResult = 1;

        long result = dao.findByRacecourseIdCount(RaceStatus.FINISHED, 1);

        assert expectedResult == result : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void findByRacecourse() throws DalException {
        RaceDao dao = getDao();

        List<Race> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getRace(2));

        List<Race> result = dao.findByRacecourse(RaceStatus.RIDING,  "Ron", 0, 1);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);

    }

    @Test
    @Tag("read")
    void findByRacecourseCount() throws DalException {
        RaceDao dao = getDao();
        long expectedResult = 1;

        long result = dao.findByRacecourseCount(RaceStatus.RIDING, "Ron");

        assert expectedResult == result : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void findInTimestampDiapason() throws DalException {
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

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void findInTimestampDiapasonCount() throws DalException {
        RaceDao dao = getDao();
        long expectedResult = 1;

        long result = dao.findInTimestampDiapasonCount(
                RaceStatus.FINISHED,
                Timestamp.valueOf("2017-03-08 00:00:00"),
                Timestamp.valueOf("2017-03-08 23:59:59")
        );

        assert expectedResult == result : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void findInTimestampDiapasonOnRacecourse() throws DalException {
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

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void findInTimestampDiapasonOnRacecourseCount() throws DalException {
        RaceDao dao = getDao();
        long expectedResult = 1;

        long result = dao.findInTimestampDiapasonOnRacecourseCount(
                RaceStatus.FINISHED,
                1,
                Timestamp.valueOf("2017-03-08 00:00:00"),
                Timestamp.valueOf("2017-03-08 23:59:59")
        );

        assert expectedResult == result : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void findByNamePart() throws DalException {
        RaceDao dao = getDao();

        List<Race> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getRace(1));

        List<Race> result = dao.findByNamePart(RaceStatus.FINISHED,  "Gem", 0, 1);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void findByNamePartCount() throws DalException {
        RaceDao dao = getDao();
        long expectedResult = 1;

        long result = dao.findByNamePartCount(RaceStatus.FINISHED, "Gem");

        assert expectedResult == result : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("write")
    void createDelete() throws DalException, ParseException {
        RaceDao dao = getDao();

        Race newEntity = Race.builder()
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

        // rollback

        storage.reload();
    }

    @Test
    @Tag("write")
    void update() throws DalException, ParseException {
        final long targetId = 1L;

        RaceDao dao = getDao();

        Race entity = dao.find(targetId);
        Race updated = Race.builder()
                .setId(entity.getId())
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
                .setPrize(2, BigDecimal.valueOf(200))
                .setPrize(3, BigDecimal.valueOf(100))
                .build();

        assert !updated.equals(entity) : "entity and updated are already same";

        dao.update(updated);

        Race afterSave = dao.find(entity.getId());

        assert updated.equals(afterSave) : defaultAssertionFailMessage(afterSave, updated);

        // rollback

        dao.update(entity);
    }


    @Test
    @Tag("write")
    void updateStatus() throws DalException {
        final long targetId = 1L;
        RaceStatus newStatus = RaceStatus.REJECTED;
        RaceStatus oldStatus = storage.getRace(targetId).getRaceStatus();
        RaceDao dao = getDao();

        Race entity = dao.find(targetId);

        assert entity.getRaceStatus() != newStatus : "entity' status and newStatus are already same";

        dao.updateStatus(entity.getId(), newStatus);

        Race afterSave = dao.find(entity.getId());

        assert afterSave.getRaceStatus() == newStatus : defaultAssertionFailMessage(
                afterSave.getRaceStatus(), newStatus
        );

        // rollback

        dao.updateStatus(entity.getId(), oldStatus);
    }
}