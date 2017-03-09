package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import static com.room414.racingbets.dal.infrastructure.TestHelper.sqlDateFromString;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author melalex
 * @version 1.0 09 Mar 2017
 */
class ParticipantDaoTest {
    private static UnitOfWork unitOfWork;

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

        Trainer trainer1 = Trainer.builder()
                .setId(1)
                .setFirstName("Ruby")
                .setSecondName("Nichols")
                .setBirthday(sqlDateFromString("1982-04-21"))
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

        Jockey jockey1 = Jockey.builder()
                .setId(1)
                .setFirstName("Ruby")
                .setSecondName("Nichols")
                .setBirthday(sqlDateFromString("1982-04-21"))
                .build();


        Horse horse1 = Horse.builder()
                .setId(1)
                .setName("Fixflex")
                .setBirthday(sqlDateFromString("2008-02-22"))
                .setGender("mare")
                .setTrainer(trainer5)
                .setOwner(owner1)
                .build();

        Participant expectedResult = Participant.builder()
                .setId(1)
                .setNumber(1)
                .setHorse(horse1)
                .setCarriedWeight(50)
                .setTopSpeed(70)
                .setOfficialRating(70)
                .setOdds(2.1)
                .setJockey(jockey1)
                .setTrainer(trainer1)
                .setPlace(1)
                .build();

        Participant result = dao.find(1L);

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
//        RacecourseDao dao = getRacecourseDao();
//        List<Racecourse> expectedResult = new LinkedList<>();
//
//        expectedResult.add(Racecourse.builder()
//                .setId(1)
//                .setName("Ronstring")
//                .setLatitude(-22.72528)
//                .setLongitude(-47.64917)
//                .setClerk("Stephen Cook")
//                .setContact("scook0@hud.gov")
//                .build()
//        );
//
//
//        List<Racecourse> result = dao.findAll(0, 1);
//
//        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void findAllLimitOffset_nonexistent_returnedEmptyList() throws DalException {
        ParticipantDao dao = getParticipantDao();

        List<Participant> result = dao.findAll(300L, 400L);

        assert result.isEmpty() : "result is not empty";
    }

    @Test
    void findAll() throws ParseException, DalException {
        // TODO: findAll test
    }

    @Test
    void count() throws DalException {
        ParticipantDao dao = getParticipantDao();
        long expectedResult = 9;

        long result = dao.count();

        assert expectedResult == result : "result != expectedResult";

    }

    @Test
    void findByHorseId() {

    }

    @Test
    void findByHorseIdCount() {

    }

    @Test
    void findByOwnerId() {

    }

    @Test
    void findByOwnerIdCount() {

    }

    @Test
    void findByJockeyId() {

    }

    @Test
    void findByJockeyIdCount() {

    }

    @Test
    void findByTrainerId() {

    }

    @Test
    void findByTrainerIdCount() {

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