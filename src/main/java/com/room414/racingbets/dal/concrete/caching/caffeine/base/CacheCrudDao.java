package com.room414.racingbets.dal.concrete.caching.caffeine.base;

import com.room414.racingbets.dal.abstraction.dao.CrudDao;
import com.room414.racingbets.dal.abstraction.cache.EntityCache;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public abstract class CacheCrudDao<T> implements CrudDao<Long, T> {
    protected CrudDao<Long, T> dao;
    protected EntityCache<T> cache;

    public CacheCrudDao(CrudDao<Long, T> dao, EntityCache<T> cache) {
        this.dao = dao;
        this.cache = cache;
    }

    @Override
    public void create(T entity) {
        dao.create(entity);
        cache.deleteManyCached();
    }

    @Override
    public T find(Long id) {
        final String key = getFindByIdKey(id);

        return cache.getOneCached(key, () -> dao.find(id));
    }

    @Override
    public List<T> findAll() {
        final String key = "search:all";

        return cache.getManyCached(key, () -> dao.findAll());
    }

    @Override
    public List<T> findAll(int offset, int limit) {
        final String key = String.format("search:all:%d:%d", limit, offset);

        return cache.getManyCached(key, () -> dao.findAll(offset, limit));
    }

    @Override
    public int count() {
        final String key = "count";

        return cache.getCachedCount(key, () -> dao.count());
    }

    @Override
    public boolean delete(Long id) {
        final String key = getFindByIdKey(id);

        cache.deleteOneCached(key);
        cache.deleteManyCached();
        return dao.delete(id);
    }

    protected String getFindByIdKey(long id) {
        return "search:" + id;
    }
}
