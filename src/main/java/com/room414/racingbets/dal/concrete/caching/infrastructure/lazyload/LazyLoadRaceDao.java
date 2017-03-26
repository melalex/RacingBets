package com.room414.racingbets.dal.concrete.caching.infrastructure.lazyload;

import com.room414.racingbets.dal.abstraction.dao.RaceDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.domain.entities.FilterParams;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.RaceStatus;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class LazyLoadRaceDao implements RaceDao {
    private UnitOfWork unitOfWork;

    public LazyLoadRaceDao(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    private RaceDao getRaceDao() {
        return unitOfWork.getRaceDao();
    }

    @Override
    public void create(Race entity) {
        getRaceDao().create(entity);
    }

    @Override
    public List<Race> filter(FilterParams params) {
        return getRaceDao().filter(params);
    }

    @Override
    public int count(FilterParams params) {
        return getRaceDao().count(params);
    }

    @Override
    public boolean updateStatus(long id, RaceStatus status) {
        return getRaceDao().updateStatus(id, status);
    }

    @Override
    public Race find(Long id) {
        return getRaceDao().find(id);
    }

    @Override
    public List<Race> findAll() {
        return getRaceDao().findAll();
    }

    @Override
    public List<Race> findAll(int offset, int limit) {
        return getRaceDao().findAll(offset, limit);
    }

    @Override
    public int count() {
        return getRaceDao().count();
    }

    @Override
    public long update(Race entity) {
        return getRaceDao().update(entity);
    }

    @Override
    public boolean delete(Long id) {
        return getRaceDao().delete(id);
    }
}
