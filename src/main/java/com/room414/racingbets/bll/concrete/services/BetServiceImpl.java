package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.infrastructure.BetError;
import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.BetService;
import com.room414.racingbets.bll.dto.entities.BetDto;
import com.room414.racingbets.bll.dto.entities.OddsDto;
import com.room414.racingbets.bll.dto.entities.ParticipantDto;
import com.room414.racingbets.dal.abstraction.dao.ApplicationUserDao;
import com.room414.racingbets.dal.abstraction.dao.BetDao;
import com.room414.racingbets.dal.abstraction.dao.RaceDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.BetStatus;
import com.room414.racingbets.dal.domain.enums.RaceStatus;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;


/**
 * @author Alexander Melashchenko
 * @version 1.0 19 Mar 2017
 */
public class BetServiceImpl implements BetService {
    private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
    private UnitOfWorkFactory factory;

    public BetServiceImpl(UnitOfWorkFactory factory) {
        this.factory = factory;
    }

    private boolean isValidBetType(BetDto bet, Consumer<BetError> onError) {
        Map<Integer, ParticipantDto> participants = bet.getParticipants();

        switch (bet.getBetType()) {
            case SHOW:
                return participants.size() == 3
                        && participants.get(1) != null
                        && participants.get(2) != null
                        && participants.get(3) != null;
            case PLACE:
                return participants.size() == 2
                        && participants.get(1) != null
                        && participants.get(2) != null;
            case WIN:
                return participants.size() == 1
                        && participants.get(1) != null;
            case QUINELLA:
                return participants.size() == 2
                        && participants.get(1) != null
                        && participants.get(2) != null;
            case EXACTA:
                return participants.size() == 2
                        && participants.get(1) != null
                        && participants.get(2) != null;
            case TRIFECTA:
                return participants.size() == 3
                        && participants.get(1) != null
                        && participants.get(2) != null
                        && participants.get(3) != null;
            case SUPERFECTA:
                return participants.size() == 4
                        && participants.get(1) != null
                        && participants.get(2) != null
                        && participants.get(3) != null
                        && participants.get(4) != null;
            default:
                onError.accept(new BetError("betType", BetError.BetErrorType.INVALID_BET_TYPE));
                return false;
        }
    }

    private boolean isValidPreconditions(BetDto bet, UnitOfWork unitOfWork, Consumer<BetError> onError) {
        boolean result = true;

        ApplicationUserDao applicationUserDao = unitOfWork.getApplicationUserDao();
        RaceDao raceDao = unitOfWork.getRaceDao();

        ApplicationUser user = applicationUserDao.find(bet.getUser().getId());

        if (!user.getEmailConfirmed()) {
            onError.accept(new BetError("user", BetError.BetErrorType.UNCONFIRMED_EMAIL));
            result = false;
        }

        if (!applicationUserDao.tryGetMoney(user.getId(), bet.getBetSize())) {
            onError.accept(new BetError("user", BetError.BetErrorType.NOT_ENOUGH_MONEY));
            result = false;
        }

        Race race = raceDao.find(bet.getRaceId());

        if (race.getRaceStatus() != RaceStatus.SCHEDULED) {
            onError.accept(new BetError("raceId", BetError.BetErrorType.INVALID_RACE_STATUS));
            result = false;
        }

        if (race.getMinBet().compareTo(bet.getBetSize()) > 0) {
            onError.accept(new BetError("betSize", BetError.BetErrorType.BET_IS_TOO_SMALL));
            result = false;
        }

        return result;
    }


    @Override
    public BigDecimal makeBet(BetDto bet, Consumer<BetError> onError) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            boolean isValidBetType = isValidBetType(bet, onError);
            boolean isValidPreconditions = isValidPreconditions(bet, unitOfWork, onError);

            if (isValidBetType && isValidPreconditions) {
                bet.setBetStatus(BetStatus.SCHEDULED);
                Bet entity = mapper.map(bet, Bet.class);

                unitOfWork.getBetDao().create(entity);

                unitOfWork.commit();

                bet.setId(entity.getId());
            }

            return unitOfWork
                    .getApplicationUserDao()
                    .find(bet.getUser().getId())
                    .getBalance();
        }
    }

    @Override
    public OddsDto getOdds(BetDto bet, Consumer<BetError> onError) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            if (isValidBetType(bet, onError)) {
                Bet entity = mapper.map(bet, Bet.class);
                Odds odds = unitOfWork.getBetDao().getOdds(entity);
                return mapper.map(odds, OddsDto.class);
            }

            return null;
        }
    }

    @Override
    public List<BetDto> getBetsByUser(long id, Pager pager) {
        int limit = pager.getLimit();
        int offset = pager.getOffset();

        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            BetDao betDao = unitOfWork.getBetDao();

            List<Bet> bets = betDao.findByUserId(id, offset, limit);
            int count = betDao.findByRaceIdCount(id);

            pager.setCount(count);

            return bets.stream()
                    .map(b -> mapper.map(b, BetDto.class))
                    .collect(Collectors.toList());
        }
    }
}
