package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.cache.ParticipantCache;
import com.room414.racingbets.dal.abstraction.dao.ParticipantDao;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.CacheCrudDao;
import com.room414.racingbets.dal.domain.entities.Participant;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
class CachedParticipantDao extends CacheCrudDao<Participant> implements ParticipantDao {
    CachedParticipantDao(ParticipantDao dao, ParticipantCache cache) {
        super(dao, cache);
        this.dao = dao;
        this.cache = cache;
    }
}
