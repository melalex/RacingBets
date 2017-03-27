package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.cache.BetCache;
import com.room414.racingbets.dal.abstraction.dao.BetDao;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.CacheCrudDao;
import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;
import com.room414.racingbets.dal.domain.entities.Race;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CachedBetDao extends CacheCrudDao<Bet> implements BetDao {
    protected BetDao dao;
    protected BetCache cache;

    CachedBetDao(BetDao dao, BetCache cache) {
        super(dao, cache);
        this.dao = dao;
        this.cache = cache;
    }

    @Override
    public void create(Bet entity) {
        dao.create(entity);
        cache.updateOdds(entity);
    }

    @Override
    public List<Bet> findByUserId(long id, int offset, int limit) {
        String key = String.format("search:user:%d:%d:%d", id, limit, offset);

        return cache.getManyCached(key, () -> dao.findByUserId(id, offset, limit));
    }

    @Override
    public int findByUserIdCount(long id) {
        String key = "search:user:id";

        return cache.getCachedCount(key, () -> dao.findByUserIdCount(id));
    }

    @Override
    public List<Bet> findByRaceId(long id, int offset, int limit) {
        String key = String.format("search:race:%d:%d:%d", id, limit, offset);

        return cache.getManyCached(key, () -> dao.findByRaceId(id, offset, limit));
    }

    @Override
    public int findByRaceIdCount(long id) {
        String key = "search:race:id";

        return cache.getCachedCount(key, () -> dao.findByRaceIdCount(id));
    }

    @Override
    public long update(List<Bet> bets) {
        cache.deleteAllCached();
        return dao.update(bets);
    }

    @Override
    public Odds getOdds(Bet bet) {
        return cache.getOdds(bet, () -> dao.getOdds(bet));
    }

    @Override
    public void fixRaceResult(Race race) {
        dao.fixRaceResult(race);
    }

    @Override
    public void rejectBets(long raceId) {
        dao.rejectBets(raceId);
    }
}
