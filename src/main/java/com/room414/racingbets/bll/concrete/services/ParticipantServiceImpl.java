package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.ParticipantService;
import com.room414.racingbets.bll.dto.entities.ParticipantDto;
import com.room414.racingbets.bll.dto.entities.RaceParticipantThumbnailDto;
import com.room414.racingbets.dal.abstraction.dao.ParticipantDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.domain.entities.RaceParticipantThumbnail;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Alexander Melashchenko
 * @version 1.0 19 Mar 2017
 */
public class ParticipantServiceImpl implements ParticipantService {
    private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
    private UnitOfWorkFactory factory;

    public ParticipantServiceImpl(UnitOfWorkFactory factory) {
        this.factory = factory;
    }

    private List<RaceParticipantThumbnailDto> mapList(List<RaceParticipantThumbnail> source) {
        return source
                .stream()
                .map(p -> mapper.map(p, RaceParticipantThumbnailDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void update(ParticipantDto participant) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Participant entity = mapper.map(participant, Participant.class);
            unitOfWork.getParticipantDao().update(entity);
            unitOfWork.commit();
        }
    }

    @Override
    public void delete(long id) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getParticipantDao().delete(id);
            unitOfWork.commit();
        }
    }

    @Override
    public List<RaceParticipantThumbnailDto> findByHorse(long id, Pager pager) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            ParticipantDao dao = unitOfWork.getParticipantDao();

            List<RaceParticipantThumbnail> list = dao.findByHorseId(id, pager.getOffset(), pager.getLimit());
            int count = dao.findByHorseIdCount(id);

            pager.setCount(count);

            return mapList(list);
        }
    }

    @Override
    public List<RaceParticipantThumbnailDto> findByOwner(long id, Pager pager) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            ParticipantDao dao = unitOfWork.getParticipantDao();

            List<RaceParticipantThumbnail> list = dao.findByOwnerId(id, pager.getOffset(), pager.getLimit());
            int count = dao.findByOwnerIdCount(id);

            pager.setCount(count);

            return mapList(list);
        }
    }

    @Override
    public List<RaceParticipantThumbnailDto> findByJockey(long id, Pager pager) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            ParticipantDao dao = unitOfWork.getParticipantDao();

            List<RaceParticipantThumbnail> list = dao.findByJockeyId(id, pager.getOffset(), pager.getLimit());
            int count = dao.findByJockeyIdCount(id);

            pager.setCount(count);

            return mapList(list);
        }
    }

    @Override
    public List<RaceParticipantThumbnailDto> findByTrainer(long id, Pager pager) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            ParticipantDao dao = unitOfWork.getParticipantDao();

            List<RaceParticipantThumbnail> list = dao.findByTrainerId(id, pager.getOffset(), pager.getLimit());
            int count = dao.findByTrainerIdCount(id);

            pager.setCount(count);

            return mapList(list);
        }
    }
}
