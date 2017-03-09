package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;
import com.room414.racingbets.dal.domain.entities.*;
import com.room414.racingbets.dal.infrastructure.EntityStorage;
import com.room414.racingbets.dal.resolvers.UnitOfWorkParameterResolver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.room414.racingbets.dal.infrastructure.TestHelper.sqlDateFromString;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author melalex
 * @version 1.0 09 Mar 2017
 */
@ExtendWith(UnitOfWorkParameterResolver.class)
class ParticipantDaoTest {
    private static UnitOfWork unitOfWork;

    private EntityStorage storage = EntityStorage.getInstance();

    @BeforeAll
    static void setUp(UnitOfWork unitOfWork) {
        ParticipantDaoTest.unitOfWork = unitOfWork;
    }

    @AfterAll
    static void tearDown() throws Exception {
        unitOfWork.close();
    }

    private static ParticipantDao getParticipantDao() {
        return unitOfWork.getParticipantDao();
    }

    @Test
    void find_existent_returnedEntity() throws ParseException, DalException {
        ParticipantDao dao = getParticipantDao();

        Participant expectedResult = storage.getParticipant(1);

        Participant result = dao.find(1L);

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void find_nullable_returnedEntity() throws ParseException, DalException {
        ParticipantDao dao = getParticipantDao();

        Participant expectedResult = storage.getParticipant(9);

        Participant result = dao.find(9L);

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void find_nonexistent_returnedNull() throws DalException {
        ParticipantDao dao = getParticipantDao();

        Participant result = dao.find(300L);

        assert result == null : "result != null";
    }

    @Test
    void findAllLimitOffset() throws ParseException, DalException {
        ParticipantDao dao = getParticipantDao();
        List<Participant> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getParticipant(1));
        expectedResult.add(storage.getParticipant(2));

        List<Participant> result = dao.findAll(0, 2);

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void findAllLimitOffset_nonexistent_returnedEmptyList() throws DalException {
        ParticipantDao dao = getParticipantDao();

        List<Participant> result = dao.findAll(300L, 400L);

        assert result.isEmpty() : "result is not empty";
    }

    @Test
    void findAll() throws ParseException, DalException {
        List<Participant> expectedResult = storage.getAllParticipants();

        List<Participant> result = getParticipantDao().findAll();

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void count() throws DalException {
        ParticipantDao dao = getParticipantDao();
        long expectedResult = 9;

        long result = dao.count();

        assert expectedResult == result : "result != expectedResult";

    }

    @Test
    void findByHorseId() throws DalException {
        final long targetId = 1;

        ParticipantDao dao = getParticipantDao();
        List<Participant> expectedResultParticipants = new LinkedList<>();
        List<Timestamp> expectedResultTimestamps = new LinkedList<>();

        expectedResultParticipants.add(storage.getParticipant(1));
        expectedResultParticipants.add(storage.getParticipant(8));

        expectedResultTimestamps.add(storage.getRace(1).getStart());
        expectedResultTimestamps.add(storage.getRace(3).getStart());

        List<Pair<Participant, Timestamp>> result = dao.findByHorseId(targetId, 0, 2);
        List<Participant> resultParticipants = result.stream().map(Pair::getFirstElement).collect(Collectors.toList());
        List<Timestamp> resultTimestamps = result.stream().map(Pair::getSecondElement).collect(Collectors.toList());

        assert resultParticipants.equals(expectedResultParticipants) : "result != expectedResultParticipants";
        assert resultTimestamps.equals(expectedResultTimestamps) : "result != expectedResultTimestamps";
    }

    @Test
    void findByHorseIdCount() throws DalException {
        final long targetId = 1;

        ParticipantDao dao = getParticipantDao();
        long expectedResult = 2;

        long result = dao.findByHorseIdCount(targetId);

        assert expectedResult == result : "result != expectedResult";
    }

    @Test
    void findByOwnerId() throws DalException {
        final long targetId = 1;

        ParticipantDao dao = getParticipantDao();
        List<Participant> expectedResultParticipants = new LinkedList<>();
        List<Timestamp> expectedResultTimestamps = new LinkedList<>();

        expectedResultParticipants.add(storage.getParticipant(1));
        expectedResultParticipants.add(storage.getParticipant(8));

        expectedResultTimestamps.add(storage.getRace(1).getStart());
        expectedResultTimestamps.add(storage.getRace(3).getStart());

        List<Pair<Participant, Timestamp>> result = dao.findByOwnerId(targetId, 0, 2);
        List<Participant> resultParticipants = result.stream().map(Pair::getFirstElement).collect(Collectors.toList());
        List<Timestamp> resultTimestamps = result.stream().map(Pair::getSecondElement).collect(Collectors.toList());

        assert resultParticipants.equals(expectedResultParticipants) : "result != expectedResultParticipants";
        assert resultTimestamps.equals(expectedResultTimestamps) : "result != expectedResultTimestamps";
    }

    @Test
    void findByOwnerIdCount() throws DalException {
        final long targetId = 1;

        ParticipantDao dao = getParticipantDao();
        long expectedResult = 2;

        long result = dao.findByOwnerIdCount(targetId);

        assert expectedResult == result : "result != expectedResult";
    }

    @Test
    void findByJockeyId() throws DalException {
        final long targetId = 1;

        ParticipantDao dao = getParticipantDao();
        List<Participant> expectedResultParticipants = new LinkedList<>();
        List<Timestamp> expectedResultTimestamps = new LinkedList<>();

        expectedResultParticipants.add(storage.getParticipant(1));
        expectedResultParticipants.add(storage.getParticipant(6));

        expectedResultTimestamps.add(storage.getRace(1).getStart());
        expectedResultTimestamps.add(storage.getRace(2).getStart());

        List<Pair<Participant, Timestamp>> result = dao.findByJockeyId(targetId, 0, 2);
        List<Participant> resultParticipants = result.stream().map(Pair::getFirstElement).collect(Collectors.toList());
        List<Timestamp> resultTimestamps = result.stream().map(Pair::getSecondElement).collect(Collectors.toList());

        assert resultParticipants.equals(expectedResultParticipants) : "result != expectedResultParticipants";
        assert resultTimestamps.equals(expectedResultTimestamps) : "result != expectedResultTimestamps";
    }

    @Test
    void findByJockeyIdCount() throws DalException {
        final long targetId = 1;

        ParticipantDao dao = getParticipantDao();
        long expectedResult = 2;

        long result = dao.findByJockeyIdCount(targetId);

        assert expectedResult == result : "result != expectedResult";
    }

    @Test
    void findByTrainerId() throws DalException {
        final long targetId = 1;

        ParticipantDao dao = getParticipantDao();
        List<Participant> expectedResultParticipants = new LinkedList<>();
        List<Timestamp> expectedResultTimestamps = new LinkedList<>();

        expectedResultParticipants.add(storage.getParticipant(1));
        expectedResultParticipants.add(storage.getParticipant(6));

        expectedResultTimestamps.add(storage.getRace(1).getStart());
        expectedResultTimestamps.add(storage.getRace(2).getStart());

        List<Pair<Participant, Timestamp>> result = dao.findByTrainerId(targetId, 0, 2);
        List<Participant> resultParticipants = result.stream().map(Pair::getFirstElement).collect(Collectors.toList());
        List<Timestamp> resultTimestamps = result.stream().map(Pair::getSecondElement).collect(Collectors.toList());

        assert resultParticipants.equals(expectedResultParticipants) : "result != expectedResultParticipants";
        assert resultTimestamps.equals(expectedResultTimestamps) : "result != expectedResultTimestamps";
    }

    @Test
    void findByTrainerIdCount() throws DalException {
        final long targetId = 1;

        ParticipantDao dao = getParticipantDao();
        long expectedResult = 2;

        long result = dao.findByTrainerIdCount(targetId);

        assert expectedResult == result : "result != expectedResult";

    }

    @Test
    void createAndDelete() throws DalException, ParseException {
//        RacecourseDao dao = getRacecourseDao();
//
//        Racecourse newEntity = Racecourse.builder()
//                .setName("Stronghold")
//                .setLatitude(14.64361)
//                .setLongitude(120.58083)
//                .setContact("jandrews6@spiegel.de")
//                .setClerk("Joyce Andrews")
//                .build();
//
//        dao.create(newEntity);
//
//        Racecourse entity1 = dao.find(newEntity.getId());
//
//        assert newEntity.equals(entity1) : "Dao did not create Entity";
//
//        dao.delete(newEntity.getId());
//
//        Racecourse entity2 = dao.find(newEntity.getId());
//
//        assert entity2 == null : "Dao did not delete Entity";
    }

    @Test
    void update() throws DalException, ParseException {
//        final long targetId = 3L;
//
//        RacecourseDao dao = getRacecourseDao();
//
//        Racecourse entity = dao.find(targetId);
//        Racecourse updated = Racecourse.builder()
//                .setId(entity.getId())
//                .setName("Stim")
//                .setLatitude(49.38858)
//                .setLongitude(16.1096)
//                .setContact("gsnyder1a@aboutads.info")
//                .setClerk("Gary Snyder")
//                .build();
//
//        assert !updated.equals(entity) : "entity and updated are already same";
//
//        dao.update(updated);
//
//        Racecourse afterSave = dao.find(targetId);
//
//        assert updated.equals(afterSave) : "updated != afterSave";
//
//        // rollback
//
//        dao.update(entity);
    }
}