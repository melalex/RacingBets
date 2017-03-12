package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.dao.RacecourseDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Racecourse;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CacheRacecourseDao implements RacecourseDao {
    private RacecourseDao racecourseDao;

    public CacheRacecourseDao(RacecourseDao racecourseDao) {
        this.racecourseDao = racecourseDao;
    }

    @Override
    public void create(Racecourse entity) throws DalException {
        racecourseDao.create(entity);
    }

    @Override
    public List<Racecourse> findByNamePart(String namePart, long offset, long limit) throws DalException {
        return racecourseDao.findByNamePart(namePart, offset, limit);
    }

    @Override
    public Racecourse find(Long id) throws DalException {
        return racecourseDao.find(id);
    }

    @Override
    public long findByNamePartCount(String namePart) throws DalException {
        return racecourseDao.findByNamePartCount(namePart);
    }

    @Override
    public List<Racecourse> findAll() throws DalException {
        return racecourseDao.findAll();
    }

    @Override
    public List<Racecourse> findAll(long offset, long limit) throws DalException {
        return racecourseDao.findAll(offset, limit);
    }

    @Override
    public long count() throws DalException {
        return racecourseDao.count();
    }

    @Override
    public long update(Racecourse entity) throws DalException {
        return racecourseDao.update(entity);
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return racecourseDao.delete(id);
    }
}
