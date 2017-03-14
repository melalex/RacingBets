package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.dao.CrudDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.caching.caffeine.CaffeineCache;

import java.io.IOException;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public abstract class CacheCrudDao<T> implements CrudDao<Long, T> {
    CrudDao<Long, T> dao;
    CaffeineCache<T> cache;

    public CacheCrudDao(CrudDao<Long, T> dao, CaffeineCache<T> cache) {
        this.dao = dao;
        this.cache = cache;
    }

    @Override
    public void create(T entity) throws DalException {
        dao.create(entity);
        cache.deleteManyCached();
    }

    @Override
    public T find(Long id) throws DalException {
        final String key = "find:" + id;

        return cache.getOneCached(key, () -> dao.find(id));
    }

    @Override
    public List<T> findAll() throws DalException {
        final String key = "find:all";

        return cache.getManyCached(key, () -> dao.findAll());
    }

    @Override
    public List<T> findAll(long offset, long limit) throws DalException {
        final String key = String.format("find:all:%d:%d", limit, offset);

        return cache.getManyCached(key, () -> dao.findAll(offset, limit));
    }

    @Override
    public long count() throws DalException {
        final String key = "count";

        return cache.getCachedCount(key, () -> dao.count());
    }

    @Override
    public long update(T entity) throws DalException {
        return dao.update(entity);
    }

    @Override
    public boolean delete(Long id) throws DalException {
        final String key = "find:" + id;

        cache.deleteOneCached(key);
        cache.deleteManyCached();
        return dao.delete(id);
    }
}
