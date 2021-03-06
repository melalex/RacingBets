package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.cache.OwnerCache;
import com.room414.racingbets.dal.abstraction.dao.OwnerDao;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.CachePersonDao;
import com.room414.racingbets.dal.concrete.caching.caffeine.caches.CaffeineOwnerCache;
import com.room414.racingbets.dal.domain.entities.Owner;


/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CachedOwnerDao extends CachePersonDao<Owner> implements OwnerDao {

    CachedOwnerDao(OwnerDao dao, OwnerCache cache) {
        super(dao, cache);
    }

    @Override
    public long update(Owner entity) {
        cache.deleteOneCached(getFindByIdKey(entity.getId()));
        cache.deleteManyCached();
        return dao.update(entity);
    }
}
