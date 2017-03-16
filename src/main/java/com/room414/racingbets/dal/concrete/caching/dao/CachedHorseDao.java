package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.cache.HorseCache;
import com.room414.racingbets.dal.abstraction.dao.HorseDao;
import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.CacheCrudDao;
import com.room414.racingbets.dal.concrete.caching.caffeine.caches.CaffeineHorseCache;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CachedHorseDao extends CacheCrudDao<Horse> implements HorseDao {
    protected HorseDao dao;
    protected HorseCache cache;

    CachedHorseDao(HorseDao dao, HorseCache cache) {
        super(dao, cache);
        this.dao = dao;
        this.cache = cache;
    }

    @Override
    public List<Horse> findByNamePart(String namePart, long offset, long limit) {
        String key = String.format("find:name:%s:%d:%d", namePart, limit, offset);

        return cache.getManyCached(key, () -> dao.findByNamePart(namePart, offset, limit));
    }

    @Override
    public long findByNamePartCount(String namePart) {
        String key = "find:name:count:" + namePart;

        return cache.getCachedCount(key, () -> dao.findByNamePartCount(namePart));
    }
}
