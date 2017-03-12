package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.dao.HorseDao;
import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.abstraction.exception.DalException;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CacheHorseDao implements HorseDao {
    private HorseDao horseDao;

    public CacheHorseDao(HorseDao horseDao) {
        this.horseDao = horseDao;
    }

    @Override
    public void create(Horse entity) throws DalException {
        horseDao.create(entity);
    }

    @Override
    public List<Horse> findByNamePart(String namePart, long offset, long limit) throws DalException {
        return horseDao.findByNamePart(namePart, offset, limit);
    }

    @Override
    public Horse find(Long id) throws DalException {
        return horseDao.find(id);
    }

    @Override
    public long findByNamePartCount(String namePart) throws DalException {
        return horseDao.findByNamePartCount(namePart);
    }

    @Override
    public List<Horse> findAll() throws DalException {
        return horseDao.findAll();
    }

    @Override
    public List<Horse> findAll(long offset, long limit) throws DalException {
        return horseDao.findAll(offset, limit);
    }

    @Override
    public long count() throws DalException {
        return horseDao.count();
    }

    @Override
    public long update(Horse entity) throws DalException {
        return horseDao.update(entity);
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return horseDao.delete(id);
    }
}
