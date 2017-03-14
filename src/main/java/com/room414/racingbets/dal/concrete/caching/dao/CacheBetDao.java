package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.dao.BetDao;
import com.room414.racingbets.dal.abstraction.dao.CrudDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.caching.caffeine.CaffeineCache;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.CacheCrudDao;
import com.room414.racingbets.dal.concrete.caching.caffeine.caches.BetCache;
import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CacheBetDao extends CacheCrudDao<Bet> implements BetDao {
    protected BetDao dao;
    protected BetCache cache;

    CacheBetDao(CrudDao<Long, Bet> dao, CaffeineCache<Bet> cache) {
        super(dao, cache);
    }

    @Override
    public void create(Bet entity) throws DalException {
        dao.create(entity);
        cache.updateOdds(entity);
    }

    @Override
    public List<Bet> findByUserId(long id, long offset, long limit) throws DalException {
        String key = String.format("find:user:%d:%d:%d", id, limit, offset);

        return cache.getManyCached(key, () -> dao.findByUserId(id, offset, limit));
    }

    @Override
    public long findByUserIdCount(long id) throws DalException {
        String key = "find:user:id";

        return cache.getCachedCount(key, () -> dao.findByUserIdCount(id));
    }

    @Override
    public List<Bet> findByRaceId(long id, long offset, long limit) throws DalException {
        String key = String.format("find:race:%d:%d:%d", id, limit, offset);

        return cache.getManyCached(key, () -> dao.findByRaceId(id, offset, limit));
    }

    @Override
    public long findByRaceIdCount(long id) throws DalException {
        String key = "find:race:id";

        return cache.getCachedCount(key, () -> dao.findByRaceIdCount(id));
    }

    @Override
    public long update(List<Bet> bets) throws DalException {
        cache.deleteAllCached();
        return dao.update(bets);
    }

    @Override
    public Odds getOdds(Bet bet) throws DalException {
        return cache.getOdds(bet, () -> dao.getOdds(bet));
    }
}
