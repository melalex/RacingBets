package com.room414.racingbets.dal.concrete.caching.proxies;

import com.room414.racingbets.dal.abstraction.dao.BetDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class LazyLoadBetDao implements BetDao {
    private UnitOfWork unitOfWork;

    public LazyLoadBetDao(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    private BetDao getBetDao() throws DalException {
        return unitOfWork.getBetDao();
    }

    @Override
    public void create(Bet entity) throws DalException {
        getBetDao().create(entity);
    }

    @Override
    public List<Bet> findByUserId(long id, long offset, long limit) throws DalException {
        return getBetDao().findByUserId(id, offset, limit);
    }

    @Override
    public Bet find(Long id) throws DalException {
        return getBetDao().find(id);
    }

    @Override
    public long findByUserIdCount(long id) throws DalException {
        return getBetDao().findByUserIdCount(id);
    }

    @Override
    public List<Bet> findAll() throws DalException {
        return getBetDao().findAll();
    }

    @Override
    public List<Bet> findByRaceId(long id, long offset, long limit) throws DalException {
        return getBetDao().findByRaceId(id, offset, limit);
    }

    @Override
    public long findByRaceIdCount(long id) throws DalException {
        return getBetDao().findByRaceIdCount(id);
    }

    @Override
    public List<Bet> findAll(long offset, long limit) throws DalException {
        return getBetDao().findAll(offset, limit);
    }

    @Override
    public long update(List<Bet> bets) throws DalException {
        return getBetDao().update(bets);
    }

    @Override
    public long count() throws DalException {
        return getBetDao().count();
    }

    @Override
    public long update(Bet entity) throws DalException {
        return getBetDao().update(entity);
    }

    @Override
    public Odds getOdds(Bet bet) throws DalException {
        return getBetDao().getOdds(bet);
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return getBetDao().delete(id);
    }
}
