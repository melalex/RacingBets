package com.room414.racingbets.dal.concrete.caching.infrastructure.lazyload;

import com.room414.racingbets.dal.abstraction.dao.HorseDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.domain.entities.Horse;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class LazyLoadHorseDao implements HorseDao {
    private UnitOfWork unitOfWork;

    public LazyLoadHorseDao(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    private HorseDao getHorseDao() {
        return unitOfWork.getHorseDao();
    }

    @Override
    public void create(Horse entity) {
        getHorseDao().create(entity);
    }

    @Override
    public List<Horse> search(String namePart, int offset, int limit) {
        return getHorseDao().search(namePart, offset, limit);
    }

    @Override
    public Horse find(Long id) {
        return getHorseDao().find(id);
    }

    @Override
    public int searchCount(String namePart) {
        return getHorseDao().searchCount(namePart);
    }

    @Override
    public List<Horse> findAll() {
        return getHorseDao().findAll();
    }

    @Override
    public List<Horse> findAll(long offset, long limit) {
        return getHorseDao().findAll(offset, limit);
    }

    @Override
    public long count() {
        return getHorseDao().count();
    }

    @Override
    public long update(Horse entity) {
        return getHorseDao().update(entity);
    }

    @Override
    public boolean delete(Long id) {
        return getHorseDao().delete(id);
    }
}
