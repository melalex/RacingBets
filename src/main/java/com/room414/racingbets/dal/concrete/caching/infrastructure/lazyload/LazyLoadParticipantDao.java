package com.room414.racingbets.dal.concrete.caching.infrastructure.lazyload;

import com.room414.racingbets.dal.abstraction.dao.ParticipantDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;
import com.room414.racingbets.dal.domain.entities.Participant;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class LazyLoadParticipantDao implements ParticipantDao {
    private UnitOfWork unitOfWork;

    public LazyLoadParticipantDao(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    private ParticipantDao getParticipantDao() {
        return unitOfWork.getParticipantDao();
    }

    @Override
    public void create(Participant entity) {
        getParticipantDao().create(entity);
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByHorseId(long id, long offset, long limit) {
        return getParticipantDao().findByHorseId(id, offset, limit);
    }

    @Override
    public Participant find(Long id) {
        return getParticipantDao().find(id);
    }

    @Override
    public long findByHorseIdCount(long id) {
        return getParticipantDao().findByHorseIdCount(id);
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByOwnerId(long id, long offset, long limit) {
        return getParticipantDao().findByOwnerId(id, offset, limit);
    }

    @Override
    public List<Participant> findAll() {
        return getParticipantDao().findAll();
    }

    @Override
    public long findByOwnerIdCount(long id) {
        return getParticipantDao().findByOwnerIdCount(id);
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByJockeyId(long id, long offset, long limit) {
        return getParticipantDao().findByJockeyId(id, offset, limit);
    }

    @Override
    public List<Participant> findAll(long offset, long limit) {
        return getParticipantDao().findAll(offset, limit);
    }

    @Override
    public long findByJockeyIdCount(long id) {
        return getParticipantDao().findByJockeyIdCount(id);
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByTrainerId(long id, long offset, long limit) {
        return getParticipantDao().findByTrainerId(id, offset, limit);
    }

    @Override
    public long count() {
        return getParticipantDao().count();
    }

    @Override
    public long findByTrainerIdCount(long id) {
        return getParticipantDao().findByTrainerIdCount(id);
    }

    @Override
    public long update(Participant entity) {
        return getParticipantDao().update(entity);
    }

    @Override
    public boolean delete(Long id) {
        return getParticipantDao().delete(id);
    }
}