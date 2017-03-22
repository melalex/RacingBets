package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.exceptions.BllException;
import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.MessageService;
import com.room414.racingbets.bll.abstraction.services.RaceService;
import com.room414.racingbets.bll.concrete.infrastrucure.ErrorHandleDecorator;
import com.room414.racingbets.bll.dto.entities.BetDto;
import com.room414.racingbets.bll.dto.entities.RaceDto;
import com.room414.racingbets.dal.abstraction.dao.ApplicationUserDao;
import com.room414.racingbets.dal.abstraction.dao.BetDao;
import com.room414.racingbets.dal.abstraction.dao.RaceDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;
import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.BetStatus;
import com.room414.racingbets.dal.domain.enums.RaceStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import static com.room414.racingbets.bll.concrete.infrastrucure.ErrorMessageUtil.defaultErrorMessage;

/**
 * @author Alexander Melashchenko
 * @version 1.0 19 Mar 2017
 */
public class RaceServiceImpl implements RaceService {
    private Log log = LogFactory.getLog(RaceServiceImpl.class);
    private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
    private UnitOfWorkFactory factory;
    private ErrorHandleDecorator<RaceDto> decorator;
    private MessageService messageService;
    private int betsPerQuery;

    public RaceServiceImpl(UnitOfWorkFactory factory, MessageService messageService, int betsPerQuery) {
        this.factory = factory;
        this.decorator = new ErrorHandleDecorator<>(factory, log);
        this.messageService = messageService;
        this.betsPerQuery = betsPerQuery;
    }

    private List<RaceDto> mapRaceList(List<Race> source) {
        return source
                .stream()
                .map(s -> mapper.map(s, RaceDto.class))
                .collect(Collectors.toList());
    }

    private List<BetDto> mapBetList(List<Bet> source) {
        return source
                .stream()
                .map(s -> mapper.map(s, BetDto.class))
                .collect(Collectors.toList());
    }

    private Pair<Timestamp, Timestamp> getDayStartAndEnd(Date date) {
        GregorianCalendar cal = new GregorianCalendar();

        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Timestamp dayStart = new Timestamp(cal.getTime().getTime());

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Timestamp dayEnd = new Timestamp(cal.getTime().getTime());

        return new Pair<>(dayStart, dayEnd);
    }

    @Override
    public void scheduleRace(RaceDto race) {
        decorator.create(race, this::create);
    }

    @Override
    // TODO: Schedule this job
    public void startRace(long id) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getRaceDao().updateStatus(id, RaceStatus.RIDING);
            unitOfWork.commit();
        } catch (DalException e) {
            String message = defaultErrorMessage("startRace", id);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = defaultErrorMessage("startRace", id);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    // TODO: isolation level
    public void rejectRace(RaceDto race) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            long raceId = race.getId();

            unitOfWork.getRaceDao().updateStatus(raceId, RaceStatus.REJECTED);

            BetDao betDao = unitOfWork.getBetDao();
            ApplicationUserDao applicationUserDao = unitOfWork.getApplicationUserDao();
            betDao.rejectBets(raceId);

            int betsCount = betDao.findByRaceIdCount(raceId);
            List<BetDto> bets;

            for (int i = 0; i < betsCount; i += betsPerQuery) {
                bets = mapBetList(betDao.findByRaceId(raceId, i, betsPerQuery));

                for (BetDto bet : bets) {
                    applicationUserDao.putMoney(bet.getUser().getId(), bet.getBetSize());
                    messageService.sendRejectMessage(bet, race);
                }
            }

            unitOfWork.commit();
        } catch (DalException e) {
            String message = defaultErrorMessage("rejectRace", race);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = defaultErrorMessage("rejectRace", race);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    private void fixRaceResult(UnitOfWork unitOfWork, RaceDto race) {
        Race raceEntity = mapper.map(race, Race.class);
        unitOfWork.getRaceDao().update(raceEntity);
        unitOfWork.getBetDao().fixRaceResult(raceEntity);
        unitOfWork.commit();
    }

    private void payOff(UnitOfWork unitOfWork, RaceDto race) {
        BetDao betDao = unitOfWork.getBetDao();

        long raceId = race.getId();
        int betsCount = betDao.findByRaceIdCount(raceId);

        List<Bet> entities;
        BetDto bet;
        Odds odds;
        BigDecimal amount;

        for (int i = 0; i < betsCount; i += betsPerQuery) {
            entities = betDao.findByRaceId(raceId, i, betsPerQuery);

            for (Bet betEntity : entities) {
                bet = mapper.map(betEntity, BetDto.class);
                if (betEntity.getBetStatus() == BetStatus.WIN) {

                    odds = betDao.getOdds(betEntity);
                    amount = betEntity.getBetSize().multiply(odds.getDecimalOdds());
                    unitOfWork.getApplicationUserDao().putMoney(betEntity.getUser().getId(), amount);
                    messageService.sendRejectMessage(bet, race);

                } else if (betEntity.getBetStatus() == BetStatus.LOSE) {
                    messageService.sendLoseMessage(bet, race);
                }
            }
        }

        unitOfWork.commit();
    }

    @Override
    // TODO: isolation level
    public void finishRace(RaceDto race) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            fixRaceResult(unitOfWork, race);
            payOff(unitOfWork, race);
        } catch (DalException e) {
            String message = defaultErrorMessage("finishRace", race);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = defaultErrorMessage("finishRace", race);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public void update(RaceDto horse) {
        decorator.update(horse, this::update);
    }

    @Override
    public void delete(long id) {
        decorator.delete(id, this::delete);
    }

    @Override
    public RaceDto find(long id) {
        return decorator.find(id, this::find);
    }

    @Override
    public List<RaceDto> findAll(Pager pager) {
        return decorator.findAll(pager, this::findAll);
    }

    @Override
    public List<RaceDto> findByRacecourse(RaceStatus status, long id, Pager pager) {
        int limit = pager.getLimit();
        int offset = pager.getOffset();

        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            RaceDao raceDao = unitOfWork.getRaceDao();

            List<Race> list = raceDao.findByRacecourseId(status, id, offset, limit);
            int count = raceDao.findByRacecourseIdCount(status, id);

            pager.setCount(count);

            return mapRaceList(list);
        } catch (DalException e) {
            String message = defaultErrorMessage("findByRacecourse", status, id, limit, offset);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = defaultErrorMessage("findByRacecourse", status, id, limit, offset);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public List<RaceDto> findByDate(RaceStatus status, Date date, Pager pager) {
        int limit = pager.getLimit();
        int offset = pager.getOffset();

        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            RaceDao raceDao = unitOfWork.getRaceDao();

            Pair<Timestamp, Timestamp> pair = getDayStartAndEnd(date);

            List<Race> list = raceDao.findInTimestampDiapason(
                    status,
                    pair.getFirstElement(),
                    pair.getSecondElement(),
                    offset,
                    limit
            );
            int count = raceDao.findInTimestampDiapasonCount(
                    status,
                    pair.getFirstElement(),
                    pair.getSecondElement()
            );

            pager.setCount(count);

            return mapRaceList(list);
        } catch (DalException e) {
            String message = defaultErrorMessage("findByDate", status, date, limit, offset);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = defaultErrorMessage("findByDate", status, date, limit, offset);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public List<RaceDto> findByDateAndRacecourse(RaceStatus status, Date date, long id, Pager pager) {
        int limit = pager.getLimit();
        int offset = pager.getOffset();

        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            RaceDao raceDao = unitOfWork.getRaceDao();

            Pair<Timestamp, Timestamp> pair = getDayStartAndEnd(date);

            List<Race> list = raceDao.findInTimestampDiapasonOnRacecourse(
                    status,
                    id,
                    pair.getFirstElement(),
                    pair.getSecondElement(),
                    offset,
                    limit
            );
            int count = raceDao.findInTimestampDiapasonOnRacecourseCount(
                    status,
                    id,
                    pair.getFirstElement(),
                    pair.getSecondElement()
            );

            pager.setCount(count);

            return mapRaceList(list);
        } catch (DalException e) {
            String message = defaultErrorMessage("findByDateAndRacecourse", status, date, id, limit, offset);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = defaultErrorMessage("findByDateAndRacecourse", status, date, id, limit, offset);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public List<RaceDto> findByName(RaceStatus status, String name, Pager pager) {
        int limit = pager.getLimit();
        int offset = pager.getOffset();

        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            RaceDao raceDao = unitOfWork.getRaceDao();

            List<Race> entities = raceDao.findByNamePart(status, name, offset, limit);
            int count = raceDao.findByNamePartCount(status, name);

            pager.setCount(count);

            return mapRaceList(entities);
        } catch (DalException e) {
            String message = defaultErrorMessage("findByName", status, name, limit, offset);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = defaultErrorMessage("findByName", status, name, limit, offset);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    private void create(UnitOfWork unitOfWork, RaceDto race) {
        race.setRaceStatus(RaceStatus.SCHEDULED);
        Race entity = mapper.map(race, Race.class);
        unitOfWork.getRaceDao().create(entity);
        unitOfWork.commit();
    }

    private void update(UnitOfWork unitOfWork, RaceDto horse) {
        Race entity = mapper.map(horse, Race.class);
        unitOfWork.getRaceDao().update(entity);
        unitOfWork.commit();
    }

    private RaceDto find(UnitOfWork unitOfWork, long id) {
        Race entity = unitOfWork.getRaceDao().find(id);
        return entity != null ? mapper.map(entity, RaceDto.class) : null;
    }

    private List<RaceDto> findAll(UnitOfWork unitOfWork, Pager pager) {
        RaceDao horseDao = unitOfWork.getRaceDao();

        List<Race> entities = horseDao.findAll(pager.getOffset(), pager.getLimit());
        int count = horseDao.count();

        pager.setCount(count);

        return mapRaceList(entities);
    }

    private void delete(UnitOfWork unitOfWork, long id) {
        unitOfWork.getRaceDao().delete(id);
        unitOfWork.commit();
    }
}
