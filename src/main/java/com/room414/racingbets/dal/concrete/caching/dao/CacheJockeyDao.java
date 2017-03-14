package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.dao.JockeyDao;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.CachePersonDao;
import com.room414.racingbets.dal.concrete.caching.caffeine.caches.JockeyCache;
import com.room414.racingbets.dal.domain.entities.Jockey;


/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CacheJockeyDao extends CachePersonDao<Jockey> implements JockeyDao {

    CacheJockeyDao(JockeyDao dao, JockeyCache cache) {
        super(dao, cache);
    }
}
