package com.room414.racingbets.dal.abstraction.dao;

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

import static com.room414.racingbets.dal.infrastructure.TestHelper.defaultAssertionFailMessage;

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

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    void find_nullable_returnedEntity() throws ParseException, DalException {
        ParticipantDao dao = getParticipantDao();

        Participant expectedResult = storage.getParticipant(9);

        Participant result = dao.find(9L);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    void find_nonexistent_returnedNull() throws DalException {
        ParticipantDao dao = getParticipantDao();

        Participant result = dao.find(300L);

        assert result == null : defaultAssertionFailMessage(result, null);
    }

    @Test
    void findAllLimitOffset() throws ParseException, DalException {
        ParticipantDao dao = getParticipantDao();
        List<Participant> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getParticipant(1));
        expectedResult.add(storage.getParticipant(2));

        List<Participant> result = dao.findAll(0, 2);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
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

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    void count() throws DalException {
        ParticipantDao dao = getParticipantDao();
        long expectedResult = 9;

        long result = dao.count();

        assert expectedResult == result : defaultAssertionFailMessage(result, expectedResult);

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

        assert resultParticipants.equals(expectedResultParticipants) : defaultAssertionFailMessage(
                resultParticipants, expectedResultParticipants
        );
        assert resultTimestamps.equals(expectedResultTimestamps) : defaultAssertionFailMessage(
                resultTimestamps, expectedResultTimestamps
        );
    }

    @Test
    void findByHorseIdCount() throws DalException {
        final long targetId = 1;

        ParticipantDao dao = getParticipantDao();
        long expectedResult = 2;

        long result = dao.findByHorseIdCount(targetId);

        assert expectedResult == result : defaultAssertionFailMessage(result, expectedResult);
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

        assert resultParticipants.equals(expectedResultParticipants) : defaultAssertionFailMessage(
                resultParticipants, expectedResultParticipants
        );
        assert resultTimestamps.equals(expectedResultTimestamps) : defaultAssertionFailMessage(
                resultTimestamps, expectedResultTimestamps
        );
    }

    @Test
    void findByOwnerIdCount() throws DalException {
        final long targetId = 1;

        ParticipantDao dao = getParticipantDao();
        long expectedResult = 2;

        long result = dao.findByOwnerIdCount(targetId);

        assert expectedResult == result : defaultAssertionFailMessage(result, expectedResult);
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

        assert resultParticipants.equals(expectedResultParticipants) : defaultAssertionFailMessage(
                resultParticipants, expectedResultParticipants
        );
        assert resultTimestamps.equals(expectedResultTimestamps) : defaultAssertionFailMessage(
                resultTimestamps, expectedResultTimestamps
        );
    }

    @Test
    void findByJockeyIdCount() throws DalException {
        final long targetId = 1;

        ParticipantDao dao = getParticipantDao();
        long expectedResult = 2;

        long result = dao.findByJockeyIdCount(targetId);

        assert expectedResult == result : defaultAssertionFailMessage(result, expectedResult);
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

        assert resultParticipants.equals(expectedResultParticipants) : defaultAssertionFailMessage(
                resultParticipants, expectedResultParticipants
        );
        assert resultTimestamps.equals(expectedResultTimestamps) : defaultAssertionFailMessage(
                resultTimestamps, expectedResultTimestamps
        );
    }

    @Test
    void findByTrainerIdCount() throws DalException {
        final long targetId = 1;

        ParticipantDao dao = getParticipantDao();
        long expectedResult = 2;

        long result = dao.findByTrainerIdCount(targetId);

        assert expectedResult == result : defaultAssertionFailMessage(result, expectedResult);

    }

    @Test
    void delete() throws DalException, ParseException {
        // TODO: test this shit
    }

    @Test
    void update() throws DalException, ParseException {
        final long targetId = 1L;

        ParticipantDao dao = getParticipantDao();

        Participant entity = dao.find(targetId);
        Participant updated = Participant.builder()
                .setId(entity.getId())
                .setNumber(4)
                .setHorse(storage.getHorse(2))
                .setCarriedWeight(10)
                .setTopSpeed(60)
                .setOfficialRating(30)
                .setOdds(3.1)
                .setJockey(storage.getJockey(2))
                .setTrainer(storage.getTrainer(3))
                .setPlace(4)
                .build();

        assert !updated.equals(entity) : "entity and updated are already same";

        dao.update(updated);

        Participant afterSave = dao.find(entity.getId());

        assert updated.equals(afterSave) : defaultAssertionFailMessage(afterSave, updated);

        // rollback

        dao.update(entity);
    }
}