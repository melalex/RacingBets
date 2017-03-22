package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.services.BetService;
import com.room414.racingbets.bll.dto.entities.BetDto;
import com.room414.racingbets.dal.abstraction.dao.ApplicationUserDao;
import com.room414.racingbets.dal.abstraction.dao.BetDao;
import com.room414.racingbets.dal.abstraction.dao.RaceDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.infrastructure.EntityStorage;
import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.room414.racingbets.dal.infrastructure.TestHelper.defaultAssertionFailMessage;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;

/**
 * @author Alexander Melashchenko
 * @version 1.0 22 Mar 2017
 */
class BetServiceImplTest {
    private EntityStorage storage = EntityStorage.getInstance();

    private ApplicationUserDao applicationUserDaoMock = Mockito.mock(ApplicationUserDao.class);
    private RaceDao raceDaoMock = Mockito.mock(RaceDao.class);
    private BetDao betDaoMock = Mockito.mock(BetDao.class);
    private UnitOfWork unitOfWorkMock = Mockito.mock(UnitOfWork.class);
    private UnitOfWorkFactory unitOfWorkMockFactory = Mockito.mock(UnitOfWorkFactory.class);
    private BetService service = new BetServiceImpl(unitOfWorkMockFactory);
    private DozerBeanMapper mapper = new DozerBeanMapper();

    BetServiceImplTest() throws NoSuchFieldException, IllegalAccessException {
        mapper.setMappingFiles(Collections.singletonList("META-INF/dozer.xml"));

        Field mapperField = BetServiceImpl.class.getDeclaredField("mapper");
        mapperField.setAccessible(true);
        mapperField.set(service, mapper);
    }

    @BeforeEach
    void setUp() {
        Mockito.when(unitOfWorkMockFactory.createUnitOfWork()).then(invocationOnMock -> unitOfWorkMock);
        Mockito.when(unitOfWorkMock.getBetDao()).then(invocationOnMock -> betDaoMock);
        Mockito.when(unitOfWorkMock.getApplicationUserDao()).then(invocationOnMock -> applicationUserDaoMock);
        Mockito.when(unitOfWorkMock.getRaceDao()).then(invocationOnMock -> raceDaoMock);
    }

    @Test
    void makeBet_validBet_SUCCESS() throws NoSuchFieldException, IllegalAccessException {
        Mockito.when(applicationUserDaoMock.tryGetMoney(anyInt(), any())).then(invocationOnMock -> true);
        Mockito.when(raceDaoMock.find(any())).then(invocationOnMock -> storage.getRace(3));

        List<BetDto> betDtoList = storage
                .getAllBets()
                .stream()
                .map(b -> mapper.map(b, BetDto.class))
                .collect(Collectors.toList());

        for (BetDto betDto : betDtoList) {
            BetService.Response response = service.makeBet(betDto);
            BetService.Response expectedResponse = BetService.Response.SUCCESS;

            assert response == expectedResponse : defaultAssertionFailMessage(response, expectedResponse);
        }
    }

    @Test
    void makeBet_inValidBet_INVALID_BET() throws NoSuchFieldException, IllegalAccessException {
        Mockito.when(applicationUserDaoMock.tryGetMoney(anyInt(), any())).then(invocationOnMock -> true);
        Mockito.when(raceDaoMock.find(any())).then(invocationOnMock -> storage.getRace(3));

        List<BetDto> betDtoList = storage
                .getAllBets()
                .stream()
                .map(b -> mapper.map(b, BetDto.class))
                .collect(Collectors.toList());


        for (BetDto betDto : betDtoList) {
            betDto.setParticipant(5, betDto.getParticipantByPlace(1));
            BetService.Response response = service.makeBet(betDto);
            BetService.Response expectedResponse = BetService.Response.INVALID_BET;

            assert response == expectedResponse : defaultAssertionFailMessage(response, expectedResponse);
        }
    }

    @Test
    void makeBet_unConfirmEmail_EMAIL_IS_NOT_CONFIRMED() {
        Mockito.when(applicationUserDaoMock.tryGetMoney(anyInt(), any())).then(invocationOnMock -> true);
        Mockito.when(raceDaoMock.find(any())).then(invocationOnMock -> storage.getRace(3));

        BetDto betDto = mapper.map(storage.getBet(1), BetDto.class);
        betDto.getUser().setEmailConfirmed(false);

        BetService.Response expectedResponse = BetService.Response.EMAIL_IS_NOT_CONFIRMED;
        BetService.Response response = service.makeBet(betDto);

        assert response == expectedResponse : defaultAssertionFailMessage(response, expectedResponse);
    }

    @Test
    void makeBet_notEnoughMoney_NOT_ENOUGH_MONEY() {
        Mockito.when(applicationUserDaoMock.tryGetMoney(anyInt(), any())).then(invocationOnMock -> false);
        Mockito.when(raceDaoMock.find(any())).then(invocationOnMock -> storage.getRace(3));

        BetDto betDto = mapper.map(storage.getBet(1), BetDto.class);

        BetService.Response expectedResponse = BetService.Response.NOT_ENOUGH_MONEY;
        BetService.Response response = service.makeBet(betDto);

        assert response == expectedResponse : defaultAssertionFailMessage(response, expectedResponse);
    }

    @Test
    void makeBet_finishedRace_RACE_IS_STARTED() {
        Mockito.when(applicationUserDaoMock.tryGetMoney(anyInt(), any())).then(invocationOnMock -> true);
        Mockito.when(raceDaoMock.find(any())).then(invocationOnMock -> storage.getRace(1));

        BetDto betDto = mapper.map(storage.getBet(1), BetDto.class);

        BetService.Response expectedResponse = BetService.Response.RACE_IS_STARTED;
        BetService.Response response = service.makeBet(betDto);

        assert response == expectedResponse : defaultAssertionFailMessage(response, expectedResponse);
    }

    @Test
    void makeBet_startedRace_RACE_IS_STARTED() {
        Mockito.when(applicationUserDaoMock.tryGetMoney(anyInt(), any())).then(invocationOnMock -> true);
        Mockito.when(raceDaoMock.find(any())).then(invocationOnMock -> storage.getRace(2));

        BetDto betDto = mapper.map(storage.getBet(1), BetDto.class);

        BetService.Response expectedResponse = BetService.Response.RACE_IS_STARTED;
        BetService.Response response = service.makeBet(betDto);

        assert response == expectedResponse : defaultAssertionFailMessage(response, expectedResponse);
    }
}