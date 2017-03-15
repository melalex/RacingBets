package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.dao.RacecourseDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.CacheCrudDao;
import com.room414.racingbets.dal.concrete.caching.caffeine.caches.RacecourseCache;
import com.room414.racingbets.dal.domain.entities.Racecourse;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CachedRacecourseDao extends CacheCrudDao<Racecourse> implements RacecourseDao {
    private RacecourseDao dao;
    private RacecourseCache cache;

    CachedRacecourseDao(RacecourseDao dao, RacecourseCache cache) {
        super(dao, cache);
        this.dao = dao;
        this.cache = cache;
    }

    @Override
    public List<Racecourse> findByNamePart(String namePart, long offset, long limit) throws DalException {
        String key = String.format("find:name:%s:%d:%d", namePart, limit, offset);

        return cache.getManyCached(key, () -> dao.findByNamePart(namePart, offset, limit));
    }

    @Override
    public long findByNamePartCount(String namePart) throws DalException {
        String key = "find:name:count" + namePart;

        return cache.getCachedCount(key, () -> dao.findByNamePartCount(namePart));
    }

}
