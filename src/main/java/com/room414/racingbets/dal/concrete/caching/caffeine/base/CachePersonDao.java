package com.room414.racingbets.dal.concrete.caching.caffeine.base;

import com.room414.racingbets.dal.abstraction.dao.PersonDao;
import com.room414.racingbets.dal.domain.base.Person;
import com.room414.racingbets.dal.abstraction.cache.EntityCache;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public abstract class CachePersonDao<T extends Person> extends CacheCrudDao<T> implements PersonDao<T> {
    protected PersonDao<T> dao;
    protected EntityCache<T> cache;

    public CachePersonDao(PersonDao<T> dao, EntityCache<T> cache) {
        super(dao, cache);
        this.dao = dao;
        this.cache = cache;
    }

    @Override
    public List<T> search(String namePart, int offset, int limit) {
        String key = String.format("find:name:%s:%d:%d", namePart, limit, offset);

        return cache.getManyCached(key, () -> dao.search(namePart, offset, limit));
    }

    @Override
    public int searchCount(String namePart) {
        String key = "find:name:count:" + namePart;

        return cache.getCachedCount(key, () -> dao.searchCount(namePart));
    }
}
