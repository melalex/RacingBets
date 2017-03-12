package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.dao.JockeyDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Jockey;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CacheJockeyDao implements JockeyDao {
    private JockeyDao jockeyDao;

    public CacheJockeyDao(JockeyDao jockeyDao) {
        this.jockeyDao = jockeyDao;
    }

    @Override
    public void create(Jockey entity) throws DalException {
        jockeyDao.create(entity);
    }

    @Override
    public List<Jockey> findByNamePart(String namePart, long offset, long limit) throws DalException {
        return jockeyDao.findByNamePart(namePart, offset, limit);
    }

    @Override
    public Jockey find(Long id) throws DalException {
        return jockeyDao.find(id);
    }

    @Override
    public long findByNamePartCount(String namePart) throws DalException {
        return jockeyDao.findByNamePartCount(namePart);
    }

    @Override
    public List<Jockey> findAll() throws DalException {
        return jockeyDao.findAll();
    }

    @Override
    public List<Jockey> findAll(long offset, long limit) throws DalException {
        return jockeyDao.findAll(offset, limit);
    }

    @Override
    public long count() throws DalException {
        return jockeyDao.count();
    }

    @Override
    public long update(Jockey entity) throws DalException {
        return jockeyDao.update(entity);
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return jockeyDao.delete(id);
    }
}
