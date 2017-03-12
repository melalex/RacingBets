package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.dao.TrainerDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Trainer;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CacheTrainerDao implements TrainerDao {
    private TrainerDao trainerDao;

    public CacheTrainerDao(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
    }

    @Override
    public void create(Trainer entity) throws DalException {
        trainerDao.create(entity);
    }

    @Override
    public List<Trainer> findByNamePart(String namePart, long offset, long limit) throws DalException {
        return trainerDao.findByNamePart(namePart, offset, limit);
    }

    @Override
    public Trainer find(Long id) throws DalException {
        return trainerDao.find(id);
    }

    @Override
    public long findByNamePartCount(String namePart) throws DalException {
        return trainerDao.findByNamePartCount(namePart);
    }

    @Override
    public List<Trainer> findAll() throws DalException {
        return trainerDao.findAll();
    }

    @Override
    public List<Trainer> findAll(long offset, long limit) throws DalException {
        return trainerDao.findAll(offset, limit);
    }

    @Override
    public long count() throws DalException {
        return trainerDao.count();
    }

    @Override
    public long update(Trainer entity) throws DalException {
        return trainerDao.update(entity);
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return trainerDao.delete(id);
    }
}
