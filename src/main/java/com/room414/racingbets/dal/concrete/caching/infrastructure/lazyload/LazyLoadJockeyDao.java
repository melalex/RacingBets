package com.room414.racingbets.dal.concrete.caching.infrastructure.lazyload;

import com.room414.racingbets.dal.abstraction.dao.JockeyDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.domain.entities.Jockey;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class LazyLoadJockeyDao implements JockeyDao {
    private UnitOfWork unitOfWork;

    public LazyLoadJockeyDao(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    private JockeyDao getJockeyDao() {
        return unitOfWork.getJockeyDao();
    }

    @Override
    public void create(Jockey entity) {
        getJockeyDao().create(entity);
    }

    @Override
    public List<Jockey> search(String namePart, int offset, int limit) {
        return getJockeyDao().search(namePart, offset, limit);
    }

    @Override
    public Jockey find(Long id) {
        return getJockeyDao().find(id);
    }

    @Override
    public int searchCount(String namePart) {
        return getJockeyDao().searchCount(namePart);
    }

    @Override
    public List<Jockey> findAll() {
        return getJockeyDao().findAll();
    }

    @Override
    public List<Jockey> findAll(int offset, int limit) {
        return getJockeyDao().findAll(offset, limit);
    }

    @Override
    public int count() {
        return getJockeyDao().count();
    }

    @Override
    public long update(Jockey entity) {
        return getJockeyDao().update(entity);
    }

    @Override
    public boolean delete(Long id) {
        return getJockeyDao().delete(id);
    }
}
