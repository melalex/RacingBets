package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;
import com.room414.racingbets.dal.domain.enums.*;
import com.room414.racingbets.dal.infrastructure.EntityStorage;
import com.room414.racingbets.dal.resolvers.UnitOfWorkParameterResolver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import static com.room414.racingbets.dal.infrastructure.TestHelper.defaultAssertionFailMessage;

/**
 * @author melalex
 * @version 1.0 09 Mar 2017
 */
// TODO: tests for Show & Superfecta
@ExtendWith(UnitOfWorkParameterResolver.class)
class BetDaoTest {
    private static UnitOfWork unitOfWork;

    private EntityStorage storage = EntityStorage.getInstance();

    @BeforeAll
    static void setUp(UnitOfWork unitOfWork) {
        BetDaoTest.unitOfWork = unitOfWork;
    }

    @AfterAll
    static void tearDown() throws Exception {
        unitOfWork.close();
    }

    private static BetDao getDao() {
        return unitOfWork.getBetDao();
    }

    @Test
    void find_existent_returnedEntity() throws DalException {
        BetDao dao = getDao();

        Bet expectedResult = storage.getBet(1);

        Bet result = dao.find(1L);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    void find_nonexistent_returnedNull() throws DalException {
        BetDao dao = getDao();

        Bet result = dao.find(300L);

        assert result == null : defaultAssertionFailMessage(result, null);
    }

    @Test
    void findAllLimitOffset() throws ParseException, DalException {
        BetDao dao = getDao();
        List<Bet> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getBet(1));
        expectedResult.add(storage.getBet(2));

        List<Bet> result = dao.findAll(0, 2);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    void findAllLimitOffset_nonexistent_returnedEmptyList() throws DalException {
        BetDao dao = getDao();

        List<Bet> result = dao.findAll(300L, 400L);

        assert result.isEmpty() : "result is not empty";
    }

    @Test
    void findAll() throws ParseException, DalException {
        List<Bet> expectedResult = storage.getAllBets();

        List<Bet> result = getDao().findAll();

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    void count() throws DalException {
        BetDao dao = getDao();
        long expectedResult = 15;

        long result = dao.count();

        assert expectedResult == result : defaultAssertionFailMessage(result, expectedResult);

    }

    @Test
    void findByUserId() throws DalException {
        BetDao dao = getDao();

        List<Bet> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getBet(1));
        expectedResult.add(storage.getBet(4));
        expectedResult.add(storage.getBet(7));
        expectedResult.add(storage.getBet(10));
        expectedResult.add(storage.getBet(13));

        List<Bet> result = dao.findByUserId(3, 0, 5);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    void findByUserIdCount() throws DalException {
        BetDao dao = getDao();
        long expectedResult = 5;

        long result = dao.findByUserIdCount(3);

        assert expectedResult == result : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    void findByRaceId() throws DalException {
        BetDao dao = getDao();

        List<Bet> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getBet(1));
        expectedResult.add(storage.getBet(2));
        expectedResult.add(storage.getBet(3));
        expectedResult.add(storage.getBet(4));
        expectedResult.add(storage.getBet(5));
        expectedResult.add(storage.getBet(6));
        expectedResult.add(storage.getBet(7));
        expectedResult.add(storage.getBet(8));
        expectedResult.add(storage.getBet(9));
        expectedResult.add(storage.getBet(10));
        expectedResult.add(storage.getBet(11));
        expectedResult.add(storage.getBet(12));
        expectedResult.add(storage.getBet(13));
        expectedResult.add(storage.getBet(14));
        expectedResult.add(storage.getBet(15));

        List<Bet> result = dao.findByRaceId(1, 0, 15);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    void findByRaceIdCount() throws DalException {
        BetDao dao = getDao();
        long expectedResult = 15;

        long result = dao.findByRaceIdCount(1);

        assert expectedResult == result : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    void getOdds_place() throws DalException {
        BetDao dao = getDao();
        Odds expectedResult = new Odds(600, 300, 0.14);
        Bet bet = Bet.builder()
                .setUser(storage.getApplicationUser(3))
                .setRaceId(1)
                .setBetType(BetType.PLACE)
                .setBetStatus(BetStatus.SCHEDULED)
                .setBetSize(BigDecimal.valueOf(100))
                .setParticipant(1, storage.getParticipant(1))
                .setParticipant(2, storage.getParticipant(1))
                .build();

        Odds result = dao.getOdds(bet);

        assert expectedResult.equals(result) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    void getOdds_win() throws DalException {
        BetDao dao = getDao();
        Odds expectedResult = new Odds(600, 300, 0.14);
        Bet bet = Bet.builder()
                .setRaceId(1)
                .setUser(storage.getApplicationUser(3))
                .setBetType(BetType.WIN)
                .setBetStatus(BetStatus.SCHEDULED)
                .setBetSize(BigDecimal.valueOf(100))
                .setParticipant(1, storage.getParticipant(1))
                .build();

        Odds result = dao.getOdds(bet);

        assert expectedResult.equals(result) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    void getOdds_qeinella() throws DalException {
        BetDao dao = getDao();
        Odds expectedResult = new Odds(600, 300, 0.14);
        Bet bet = Bet.builder()
                .setRaceId(1)
                .setUser(storage.getApplicationUser(3))
                .setBetType(BetType.QUINELLA)
                .setBetStatus(BetStatus.SCHEDULED)
                .setBetSize(BigDecimal.valueOf(100))
                .setParticipant(1, storage.getParticipant(1))
                .setParticipant(2, storage.getParticipant(2))
                .build();

        Odds result = dao.getOdds(bet);

        assert expectedResult.equals(result) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    void getOdds_exacta() throws DalException {
        BetDao dao = getDao();
        Odds expectedResult = new Odds(600, 300, 0.14);
        Bet bet = Bet.builder()
                .setRaceId(1)
                .setUser(storage.getApplicationUser(3))
                .setBetType(BetType.EXACTA)
                .setBetStatus(BetStatus.SCHEDULED)
                .setBetSize(BigDecimal.valueOf(100))
                .setParticipant(1, storage.getParticipant(1))
                .setParticipant(2, storage.getParticipant(2))
                .build();

        Odds result = dao.getOdds(bet);

        assert expectedResult.equals(result) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    void getOdds_trifecta() throws DalException {
        BetDao dao = getDao();
        Odds expectedResult = new Odds(600, 600, 0.14);
        Bet bet = Bet.builder()
                .setRaceId(1)
                .setUser(storage.getApplicationUser(3))
                .setBetType(BetType.TRIFECTA)
                .setBetStatus(BetStatus.SCHEDULED)
                .setBetSize(BigDecimal.valueOf(100))
                .setParticipant(1, storage.getParticipant(1))
                .setParticipant(2, storage.getParticipant(2))
                .setParticipant(3, storage.getParticipant(3))
                .build();

        Odds result = dao.getOdds(bet);

        assert expectedResult.equals(result) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    void createDelete() throws DalException, ParseException {
        BetDao dao = getDao();

        Bet newEntity = Bet.builder()
                .setRaceId(1)
                .setUser(storage.getApplicationUser(3))
                .setBetType(BetType.PLACE)
                .setBetStatus(BetStatus.SCHEDULED)
                .setBetSize(BigDecimal.valueOf(100))
                .setParticipant(1, storage.getParticipant(1))
                .setParticipant(2, storage.getParticipant(1))
                .build();

        dao.create(newEntity);

        Bet entity1 = dao.find(newEntity.getId());

        assert newEntity.equals(entity1) : "Dao did not create entity";

        dao.delete(newEntity.getId());

        Bet entity2 = dao.find(newEntity.getId());

        assert entity2 == null : "Dao did not delete entity";
    }

    @Test
    void update() throws DalException, ParseException {
        // TODO: bet update is redundant
    }
}