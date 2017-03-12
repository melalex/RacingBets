package com.room414.racingbets.dal.concrete.caching.lazyload;

import com.room414.racingbets.dal.abstraction.dao.JockeyDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
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

    private JockeyDao getJockeyDao() throws DalException {
        return unitOfWork.getJockeyDao();
    }

    @Override
    public void create(Jockey entity) throws DalException {
        getJockeyDao().create(entity);
    }

    @Override
    public List<Jockey> findByNamePart(String namePart, long offset, long limit) throws DalException {
        return getJockeyDao().findByNamePart(namePart, offset, limit);
    }

    @Override
    public Jockey find(Long id) throws DalException {
        return getJockeyDao().find(id);
    }

    @Override
    public long findByNamePartCount(String namePart) throws DalException {
        return getJockeyDao().findByNamePartCount(namePart);
    }

    @Override
    public List<Jockey> findAll() throws DalException {
        return getJockeyDao().findAll();
    }

    @Override
    public List<Jockey> findAll(long offset, long limit) throws DalException {
        return getJockeyDao().findAll(offset, limit);
    }

    @Override
    public long count() throws DalException {
        return getJockeyDao().count();
    }

    @Override
    public long update(Jockey entity) throws DalException {
        return getJockeyDao().update(entity);
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return getJockeyDao().delete(id);
    }
}
