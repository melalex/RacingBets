package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.services.ParticipantService;
import com.room414.racingbets.bll.dto.entities.ParticipantDto;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.Participant;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

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
}
