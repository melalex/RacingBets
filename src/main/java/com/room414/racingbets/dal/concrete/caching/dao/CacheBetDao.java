package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.dao.BetDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CacheBetDao implements BetDao {
    private BetDao betDao;

    public CacheBetDao(BetDao betDao) {
        this.betDao = betDao;
    }

    @Override
    public void create(Bet entity) throws DalException {
        betDao.create(entity);
    }

    @Override
    public List<Bet> findByUserId(long id, long offset, long limit) throws DalException {
        return betDao.findByUserId(id, offset, limit);
    }

    @Override
    public Bet find(Long id) throws DalException {
        return betDao.find(id);
    }

    @Override
    public long findByUserIdCount(long id) throws DalException {
        return betDao.findByUserIdCount(id);
    }

    @Override
    public List<Bet> findAll() throws DalException {
        return betDao.findAll();
    }

    @Override
    public List<Bet> findByRaceId(long id, long offset, long limit) throws DalException {
        return betDao.findByRaceId(id, offset, limit);
    }

    @Override
    public long findByRaceIdCount(long id) throws DalException {
        return betDao.findByRaceIdCount(id);
    }

    @Override
    public List<Bet> findAll(long offset, long limit) throws DalException {
        return betDao.findAll(offset, limit);
    }

    @Override
    public long update(List<Bet> bets) throws DalException {
        return betDao.update(bets);
    }

    @Override
    public long count() throws DalException {
        return betDao.count();
    }

    @Override
    public long update(Bet entity) throws DalException {
        return betDao.update(entity);
    }

    @Override
    public Odds getOdds(Bet bet) throws DalException {
        return betDao.getOdds(bet);
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return betDao.delete(id);
    }
}
