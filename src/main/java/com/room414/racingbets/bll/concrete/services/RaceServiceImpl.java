package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.infrastructure.FilterParamsBuilder;
import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.MessageService;
import com.room414.racingbets.bll.abstraction.services.RaceService;
import com.room414.racingbets.bll.dto.entities.BetDto;
import com.room414.racingbets.bll.dto.entities.RaceDto;
import com.room414.racingbets.dal.abstraction.dao.ApplicationUserDao;
import com.room414.racingbets.dal.abstraction.dao.BetDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;
import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.FilterParams;
import com.room414.racingbets.dal.domain.entities.Odds;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.BetStatus;
import com.room414.racingbets.dal.domain.enums.RaceStatus;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Alexander Melashchenko
 * @version 1.0 19 Mar 2017
 */
public class RaceServiceImpl implements RaceService {
    private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
    private UnitOfWorkFactory factory;
    private MessageService messageService;
    private int betsPerQuery;

    public RaceServiceImpl(UnitOfWorkFactory factory, MessageService messageService, int betsPerQuery) {
        this.factory = factory;
        this.messageService = messageService;
        this.betsPerQuery = betsPerQuery;
    }

    static class FilterParamsBuilderImpl implements FilterParamsBuilder {
        private RaceStatus raceStatus;
        private Long racecourseId;
        private Long horseId;
        private Long trainerId;
        private Long jockeyId;
        private String name;
        private Timestamp begin;
        private Timestamp end;
        private int limit;
        private int offset;

        private Pair<Timestamp, Timestamp> getDayStartAndEnd(long seconds) {
            GregorianCalendar cal = new GregorianCalendar();

            cal.setTime(new Date(seconds));
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

        private Long parseLong(String value) {
            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                return null;
            }

        }

        @Override
        public FilterParamsBuilder setRaceStatus(String raceStatus) {
            this.raceStatus = RaceStatus.getRaceStatus(raceStatus);
            return this;
        }

        @Override
        public FilterParamsBuilder setRacecourseId(String racecourseId) {
            this.racecourseId = parseLong(racecourseId);
            return this;
        }

        @Override
        public FilterParamsBuilder setHorseId(String horseId) {
            this.horseId = parseLong(horseId);
            return this;
        }

        @Override
        public FilterParamsBuilder setTrainerId(String trainerId) {
            this.trainerId = parseLong(trainerId);
            return this;
        }

        @Override
        public FilterParamsBuilder setJockeyId(String jockeyId) {
            this.jockeyId = parseLong(jockeyId);
            return this;
        }

        @Override
        public FilterParamsBuilder setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public FilterParamsBuilder setDate(String date) {
            Long seconds = parseLong(date);
            if (seconds != null) {
                Pair<Timestamp, Timestamp> beginAndEnd = getDayStartAndEnd(seconds);
                this.begin = beginAndEnd.getFirstElement();
                this.end = beginAndEnd.getSecondElement();
            }
            return this;
        }

        @Override
        public FilterParamsBuilder setLimit(int limit) {
            this.limit = limit;
            return this;
        }

        @Override
        public FilterParamsBuilder setOffset(int offset) {
            this.offset = offset;
            return this;
        }

        @Override
        public FilterParams build() {
            FilterParams params = new FilterParams();

            params.setRaceStatus(raceStatus);
            params.setRacecourseId(racecourseId);
            params.setHorseId(horseId);
            params.setTrainerId(trainerId);
            params.setJockeyId(jockeyId);
            params.setName(name);
            params.setBegin(begin);
            params.setEnd(end);
            params.setLimit(limit);
            params.setOffset(offset);

            return params;
        }
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

    @Override
    public void scheduleRace(RaceDto race) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            race.setRaceStatus(RaceStatus.SCHEDULED);
            Race entity = mapper.map(race, Race.class);
            unitOfWork.getRaceDao().create(entity);
            unitOfWork.commit();
            race.setId(entity.getId());
        }
    }

    @Override
    // TODO: Schedule this job
    public void startRace(long id) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getRaceDao().updateStatus(id, RaceStatus.RIDING);
            unitOfWork.commit();
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
                    messageService.sendWinMessage(bet, race);

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
        }
    }

    @Override
    public void update(RaceDto race) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Race entity = mapper.map(race, Race.class);
            unitOfWork.getRaceDao().update(entity);
            unitOfWork.commit();
        }
    }

    @Override
    public void delete(long id) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getRaceDao().delete(id);
            unitOfWork.commit();
        }
    }

    @Override
    public RaceDto find(long id) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Race entity = unitOfWork.getRaceDao().find(id);
            return entity != null ? mapper.map(entity, RaceDto.class) : null;
        }
    }

    @Override
    public FilterParamsBuilder filterParamsBuilder() {
        return new FilterParamsBuilderImpl();
    }

    @Override
    public List<RaceDto> filter(FilterParamsBuilder builder, Pager pager) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            builder.setLimit(pager.getLimit());
            builder.setOffset(pager.getOffset());
            FilterParams params = builder.build();

            List<Race> entities = unitOfWork.getRaceDao().filter(params);
            int count = unitOfWork.getRaceDao().count(params);

            pager.setCount(count);

            return mapRaceList(entities);
        }
    }

}
