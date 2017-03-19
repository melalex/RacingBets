package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.exceptions.BllException;
import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.abstraction.services.BetService;
import com.room414.racingbets.bll.dto.entities.BetDto;
import com.room414.racingbets.bll.dto.entities.OddsDto;
import com.room414.racingbets.bll.dto.entities.ParticipantDto;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.enums.RaceStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Alexander Melashchenko
 * @version 1.0 19 Mar 2017
 */
public class BetServiceImpl implements BetService {
    private Log log = LogFactory.getLog(BetService.class);
    private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
    private UnitOfWorkFactory factory;

    public BetServiceImpl(UnitOfWorkFactory factory) {
        this.factory = factory;
    }

    private boolean isBetValid(BetDto bet) {
        Map<Integer, ParticipantDto> participants = bet.getParticipants();

        switch (bet.getBetType()) {
            case SHOW:
                return participants.size() == 3
                        && participants.get(1) != null
                        && participants.get(2) != null
                        && participants.get(3) != null;
            case PLACE:
                return participants.size() == 3
                        && participants.get(1) != null
                        && participants.get(2) != null;
            case WIN:
                return participants.size() == 3
                        && participants.get(1) != null;
            case QUINELLA:
                return participants.size() == 3
                        && participants.get(1) != null
                        && participants.get(2) != null;
            case EXACTA:
                return participants.size() == 3
                        && participants.get(1) != null
                        && participants.get(2) != null;
            case TRIFECTA:
                return participants.size() == 3
                        && participants.get(1) != null
                        && participants.get(2) != null
                        && participants.get(3) != null;
            case SUPERFECTA:
                return participants.size() == 3
                        && participants.get(1) != null
                        && participants.get(2) != null
                        && participants.get(3) != null
                        && participants.get(4) != null;
            default:
                return false;
        }
    }

    @Override
    public Response makeBet(BetDto bet) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            if (!isBetValid(bet)) {
                return Response.INVALID_BET;
            }

            boolean moneyTaken = unitOfWork
                    .getApplicationUserDao()
                    .tryGetMoney(bet.getUser().getId(), bet.getBetSize());

            if (!moneyTaken) {
                return Response.NOT_ENOUGH_MONEY;
            }

            RaceStatus raceStatus = unitOfWork
                    .getRaceDao()
                    .find(bet.getRaceId())
                    .getRaceStatus();

            if (raceStatus != RaceStatus.SCHEDULED) {
                return Response.RACE_IS_STARTED;
            }

            Bet entity = mapper.map(bet, Bet.class);

            unitOfWork.getBetDao().create(entity);

            unitOfWork.commit();

            return Response.SUCCESS;
        } catch (DalException e) {
            String message = "Exception during bet making";
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = "Exception during bet making";
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public OddsDto getOdds(BetDto bet) {
        return null;
    }

    @Override
    public List<BetDto> getBetsByUser(long id, Pager pager) {
        return null;
    }
}
