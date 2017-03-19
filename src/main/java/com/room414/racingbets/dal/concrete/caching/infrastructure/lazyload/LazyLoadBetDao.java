package com.room414.racingbets.dal.concrete.caching.infrastructure.lazyload;

import com.room414.racingbets.dal.abstraction.dao.BetDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
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

    private BetDao getBetDao() {
        return unitOfWork.getBetDao();
    }

    @Override
    public void create(Bet entity) {
        getBetDao().create(entity);
    }

    @Override
    public List<Bet> findByUserId(long id, int offset, int limit) {
        return getBetDao().findByUserId(id, offset, limit);
    }

    @Override
    public Bet find(Long id) {
        return getBetDao().find(id);
    }

    @Override
    public int findByUserIdCount(long id) {
        return getBetDao().findByUserIdCount(id);
    }

    @Override
    public List<Bet> findAll() {
        return getBetDao().findAll();
    }

    @Override
    public List<Bet> findByRaceId(long id, int offset, int limit) {
        return getBetDao().findByRaceId(id, offset, limit);
    }

    @Override
    public int findByRaceIdCount(long id) {
        return getBetDao().findByRaceIdCount(id);
    }

    @Override
    public List<Bet> findAll(int offset, int limit) {
        return getBetDao().findAll(offset, limit);
    }

    @Override
    public long update(List<Bet> bets) {
        return getBetDao().update(bets);
    }

    @Override
    public int count() {
        return getBetDao().count();
    }

    @Override
    public long update(Bet entity) {
        return getBetDao().update(entity);
    }

    @Override
    public Odds getOdds(Bet bet) {
        return getBetDao().getOdds(bet);
    }

    @Override
    public boolean delete(Long id) {
        return getBetDao().delete(id);
    }
}
