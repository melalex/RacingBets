package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.cache.RacecourseCache;
import com.room414.racingbets.dal.abstraction.dao.RacecourseDao;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.CacheCrudDao;
import com.room414.racingbets.dal.concrete.caching.caffeine.caches.CaffeineRacecourseCache;
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
    public List<Racecourse> search(String namePart, int offset, int limit) {
        String key = String.format("search:name:%s:%d:%d", namePart, limit, offset);

        return cache.getManyCached(key, () -> dao.search(namePart, offset, limit));
    }

    @Override
    public int searchCount(String namePart) {
        String key = "search:name:count" + namePart;

        return cache.getCachedCount(key, () -> dao.searchCount(namePart));
    }

    @Override
    public long update(Racecourse entity) {
        cache.deleteOneCached(getFindByIdKey(entity.getId()));
        cache.deleteManyCached();
        return dao.update(entity);
    }
}
