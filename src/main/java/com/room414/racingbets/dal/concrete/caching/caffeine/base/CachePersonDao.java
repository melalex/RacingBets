package com.room414.racingbets.dal.concrete.caching.caffeine.base;

import com.room414.racingbets.dal.abstraction.dao.PersonDao;
import com.room414.racingbets.dal.abstraction.entities.Person;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.caching.caffeine.CaffeineCache;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public abstract class CachePersonDao<T extends Person> extends CacheCrudDao<T> implements PersonDao<T> {
    protected PersonDao<T> dao;
    protected CaffeineCache<T> cache;

    public CachePersonDao(PersonDao<T> dao, CaffeineCache<T> cache) {
        super(dao, cache);
        this.dao = dao;
        this.cache = cache;
    }

    @Override
    public List<T> findByNamePart(String namePart, long offset, long limit) {
        String key = String.format("find:name:%s:%d:%d", namePart, limit, offset);

        return cache.getManyCached(key, () -> dao.findByNamePart(namePart, offset, limit));
    }

    @Override
    public long findByNamePartCount(String namePart) {
        String key = "find:name:count:" + namePart;

        return cache.getCachedCount(key, () -> dao.findByNamePartCount(namePart));
    }
}
