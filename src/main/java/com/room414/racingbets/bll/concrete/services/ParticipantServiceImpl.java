package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.exceptions.BllException;
import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.abstraction.services.ParticipantService;
import com.room414.racingbets.bll.dto.entities.ParticipantDto;
import com.room414.racingbets.dal.abstraction.dao.ParticipantDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;
import com.room414.racingbets.dal.domain.entities.Participant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.room414.racingbets.bll.concrete.infrastrucure.ErrorMessageUtil.deleteErrorMessage;
import static com.room414.racingbets.bll.concrete.infrastrucure.ErrorMessageUtil.updateErrorMessage;

/**
 * @author Alexander Melashchenko
 * @version 1.0 19 Mar 2017
 */
public class ParticipantServiceImpl implements ParticipantService {
    private Log log = LogFactory.getLog(ParticipantServiceImpl.class);
    private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
    private UnitOfWorkFactory factory;

    public ParticipantServiceImpl(UnitOfWorkFactory factory) {
        this.factory = factory;
    }

    @Override
    public void update(ParticipantDto horse) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Participant entity = mapper.map(horse, Participant.class);
            unitOfWork.getParticipantDao().update(entity);
            unitOfWork.commit();
        } catch (DalException e) {
            String message = updateErrorMessage(horse);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = updateErrorMessage(horse);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }


    @Override
    public void delete(long id) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getParticipantDao().delete(id);
            unitOfWork.commit();
        } catch (DalException e) {
            String message = deleteErrorMessage(id);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = deleteErrorMessage(id);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    private String findErrorMessage(String entityName, long id, int limit, int offset) {
        return String.format(
                "Exception during finding by %s with id %d on [%d; %d]",
                entityName,
                id,
                offset,
                offset + limit
        );
    }

    private List<Pair<ParticipantDto, Date>> mapList(List<Pair<Participant, Timestamp>> source) {
        return source
                .stream()
                .map(p -> {
                    ParticipantDto participant = mapper.map(p.getFirstElement(), ParticipantDto.class);
                    Date date = new Date(p.getSecondElement().getTime());
                    return new Pair<>(participant, date);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Pair<ParticipantDto, Date>> findByHorse(long id, Pager pager) {
        int limit = pager.getLimit();
        int offset = pager.getOffset();

        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            ParticipantDao participantDao = unitOfWork.getParticipantDao();

            List<Pair<Participant, Timestamp>> list = participantDao.findByHorseId(id, offset, limit);
            int count = participantDao.findByHorseIdCount(id);

            pager.setCount(count);

            return mapList(list);
        } catch (DalException e) {
            String message = findErrorMessage("Horse", id, limit, offset);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = findErrorMessage("Horse", id, limit, offset);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public List<Pair<ParticipantDto, Date>> findByOwner(long id, Pager pager) {
        int limit = pager.getLimit();
        int offset = pager.getOffset();

        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            ParticipantDao participantDao = unitOfWork.getParticipantDao();

            List<Pair<Participant, Timestamp>> list = participantDao.findByOwnerId(id, offset, limit);
            int count = participantDao.findByOwnerIdCount(id);

            pager.setCount(count);

            return mapList(list);
        } catch (DalException e) {
            String message = findErrorMessage("Owner", id, limit, offset);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = findErrorMessage("Owner", id, limit, offset);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public List<Pair<ParticipantDto, Date>> findByJockey(long id, Pager pager) {
        int limit = pager.getLimit();
        int offset = pager.getOffset();

        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            ParticipantDao participantDao = unitOfWork.getParticipantDao();

            List<Pair<Participant, Timestamp>> list = participantDao.findByJockeyId(id, offset, limit);
            int count = participantDao.findByJockeyIdCount(id);

            pager.setCount(count);

            return mapList(list);
        } catch (DalException e) {
            String message = findErrorMessage("Jockey", id, limit, offset);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = findErrorMessage("Jockey", id, limit, offset);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public List<Pair<ParticipantDto, Date>> findByTrainer(long id, Pager pager) {
        int limit = pager.getLimit();
        int offset = pager.getOffset();

        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            ParticipantDao participantDao = unitOfWork.getParticipantDao();

            List<Pair<Participant, Timestamp>> list = participantDao.findByTrainerId(id, offset, limit);
            int count = participantDao.findByTrainerIdCount(id);

            pager.setCount(count);

            return mapList(list);
        } catch (DalException e) {
            String message = findErrorMessage("Trainer", id, limit, offset);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = findErrorMessage("Trainer", id, limit, offset);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }
}
