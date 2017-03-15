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

    private TrainerDao getTrainerDao() {
        return unitOfWork.getTrainerDao();
    }

    @Override
    public void create(Trainer entity) {
        getTrainerDao().create(entity);
    }

    @Override
    public List<Trainer> findByNamePart(String namePart, long offset, long limit) {
        return getTrainerDao().findByNamePart(namePart, offset, limit);
    }

    @Override
    public Trainer find(Long id) {
        return getTrainerDao().find(id);
    }

    @Override
    public long findByNamePartCount(String namePart) {
        return getTrainerDao().findByNamePartCount(namePart);
    }

    @Override
    public List<Trainer> findAll() {
        return getTrainerDao().findAll();
    }

    @Override
    public List<Trainer> findAll(long offset, long limit) {
        return getTrainerDao().findAll(offset, limit);
    }

    @Override
    public long count() {
        return getTrainerDao().count();
    }

    @Override
    public long update(Trainer entity) {
        return getTrainerDao().update(entity);
    }

    @Override
    public boolean delete(Long id) {
        return getTrainerDao().delete(id);
    }
}
