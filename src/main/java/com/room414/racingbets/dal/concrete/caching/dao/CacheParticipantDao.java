package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.dao.ParticipantDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;
import com.room414.racingbets.dal.domain.entities.Participant;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CacheParticipantDao implements ParticipantDao {
    private ParticipantDao participantDao;

    public CacheParticipantDao(ParticipantDao participantDao) {
        this.participantDao = participantDao;
    }

    @Override
    public void create(Participant entity) throws DalException {
        participantDao.create(entity);
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByHorseId(long id, long offset, long limit) throws DalException {
        return participantDao.findByHorseId(id, offset, limit);
    }

    @Override
    public Participant find(Long id) throws DalException {
        return participantDao.find(id);
    }

    @Override
    public long findByHorseIdCount(long id) throws DalException {
        return participantDao.findByHorseIdCount(id);
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByOwnerId(long id, long offset, long limit) throws DalException {
        return participantDao.findByOwnerId(id, offset, limit);
    }

    @Override
    public List<Participant> findAll() throws DalException {
        return participantDao.findAll();
    }

    @Override
    public long findByOwnerIdCount(long id) throws DalException {
        return participantDao.findByOwnerIdCount(id);
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByJockeyId(long id, long offset, long limit) throws DalException {
        return participantDao.findByJockeyId(id, offset, limit);
    }

    @Override
    public List<Participant> findAll(long offset, long limit) throws DalException {
        return participantDao.findAll(offset, limit);
    }

    @Override
    public long findByJockeyIdCount(long id) throws DalException {
        return participantDao.findByJockeyIdCount(id);
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByTrainerId(long id, long offset, long limit) throws DalException {
        return participantDao.findByTrainerId(id, offset, limit);
    }

    @Override
    public long count() throws DalException {
        return participantDao.count();
    }

    @Override
    public long findByTrainerIdCount(long id) throws DalException {
        return participantDao.findByTrainerIdCount(id);
    }

    @Override
    public long update(Participant entity) throws DalException {
        return participantDao.update(entity);
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return participantDao.delete(id);
    }
}
