package com.room414.racingbets.dal.concrete.caching.infrastructure.lazyload;

import com.room414.racingbets.dal.abstraction.dao.TrainerDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Trainer;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class LazyLoadTrainerDao implements TrainerDao {
    private UnitOfWork unitOfWork;

    public LazyLoadTrainerDao(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    private TrainerDao getTrainerDao() throws DalException {
        return unitOfWork.getTrainerDao();
    }

    @Override
    public void create(Trainer entity) throws DalException {
        getTrainerDao().create(entity);
    }

    @Override
    public List<Trainer> findByNamePart(String namePart, long offset, long limit) throws DalException {
        return getTrainerDao().findByNamePart(namePart, offset, limit);
    }

    @Override
    public Trainer find(Long id) throws DalException {
        return getTrainerDao().find(id);
    }

    @Override
    public long findByNamePartCount(String namePart) throws DalException {
        return getTrainerDao().findByNamePartCount(namePart);
    }

    @Override
    public List<Trainer> findAll() throws DalException {
        return getTrainerDao().findAll();
    }

    @Override
    public List<Trainer> findAll(long offset, long limit) throws DalException {
        return getTrainerDao().findAll(offset, limit);
    }

    @Override
    public long count() throws DalException {
        return getTrainerDao().count();
    }

    @Override
    public long update(Trainer entity) throws DalException {
        return getTrainerDao().update(entity);
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return getTrainerDao().delete(id);
    }
}
