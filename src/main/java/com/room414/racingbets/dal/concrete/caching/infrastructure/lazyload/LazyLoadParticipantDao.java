package com.room414.racingbets.dal.concrete.caching.infrastructure.lazyload;

import com.room414.racingbets.dal.abstraction.dao.ParticipantDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.domain.entities.RaceParticipantThumbnail;

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
    public List<RaceParticipantThumbnail> findByHorseId(long id, int offset, int limit) {
        return getParticipantDao().findByHorseId(id, offset, limit);
    }

    @Override
    public Participant find(Long id) {
        return getParticipantDao().find(id);
    }

    @Override
    public int findByHorseIdCount(long id) {
        return getParticipantDao().findByHorseIdCount(id);
    }

    @Override
    public List<RaceParticipantThumbnail> findByOwnerId(long id, int offset, int limit) {
        return getParticipantDao().findByOwnerId(id, offset, limit);
    }

    @Override
    public List<Participant> findAll() {
        return getParticipantDao().findAll();
    }

    @Override
    public int findByOwnerIdCount(long id) {
        return getParticipantDao().findByOwnerIdCount(id);
    }

    @Override
    public List<RaceParticipantThumbnail> findByJockeyId(long id, int offset, int limit) {
        return getParticipantDao().findByJockeyId(id, offset, limit);
    }

    @Override
    public List<Participant> findAll(int offset, int limit) {
        return getParticipantDao().findAll(offset, limit);
    }

    @Override
    public int findByJockeyIdCount(long id) {
        return getParticipantDao().findByJockeyIdCount(id);
    }

    @Override
    public List<RaceParticipantThumbnail> findByTrainerId(long id, int offset, int limit) {
        return getParticipantDao().findByTrainerId(id, offset, limit);
    }

    @Override
    public int count() {
        return getParticipantDao().count();
    }

    @Override
    public int findByTrainerIdCount(long id) {
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
