package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.cache.ParticipantCache;
import com.room414.racingbets.dal.abstraction.dao.ParticipantDao;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.CacheCrudDao;
import com.room414.racingbets.dal.domain.entities.Participant;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CachedParticipantDao extends CacheCrudDao<Participant> implements ParticipantDao {
    private ParticipantDao dao;
    private ParticipantCache cache;

    CachedParticipantDao(ParticipantDao dao, ParticipantCache cache) {
        super(dao, cache);
        this.dao = dao;
        this.cache = cache;
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByHorseId(long id, int offset, int limit) {
        final String key = String.format("find:horse:%d:%d:%d", id, limit, offset);

        return cache.getWhoAndWhenCached(key, () -> dao.findByHorseId(id, offset, limit));
    }

    @Override
    public int findByHorseIdCount(long id) {
        final String key = String.format("find:horse:count:%d", id);

        return cache.getCachedCount(key, () -> dao.findByHorseIdCount(id));
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByOwnerId(long id, int offset, int limit) {
        final String key = String.format("find:owner:%d:%d:%d", id, limit, offset);

        return cache.getWhoAndWhenCached(key, () -> dao.findByOwnerId(id, offset, limit));
    }

    @Override
    public int findByOwnerIdCount(long id) {
        final String key = String.format("find:owner:count:%d", id);

        return cache.getCachedCount(key, () -> dao.findByOwnerIdCount(id));
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByJockeyId(long id, int offset, int limit) {
        final String key = String.format("find:jockey:%d:%d:%d", id, limit, offset);

        return cache.getWhoAndWhenCached(key, () -> dao.findByJockeyId(id, offset, limit));
    }

    @Override
    public int findByJockeyIdCount(long id) {
        final String key = String.format("find:jockey:count:%d", id);

        return cache.getCachedCount(key, () -> dao.findByJockeyIdCount(id));
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByTrainerId(long id, int offset, int limit) {
        final String key = String.format("find:trainer:%d:%d:%d", id, limit, offset);

        return cache.getWhoAndWhenCached(key, () -> dao.findByTrainerId(id, offset, limit));
    }

    @Override
    public int findByTrainerIdCount(long id) {
        final String key = String.format("find:trainer:count:%d", id);

        return cache.getCachedCount(key, () -> dao.findByTrainerIdCount(id));
    }
}
