package com.room414.racingbets.dal.concrete.caching.lazyload;

import com.room414.racingbets.dal.abstraction.dao.RacecourseDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Racecourse;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class LazyLoadRacecourseDao implements RacecourseDao {
    private UnitOfWork unitOfWork;

    public LazyLoadRacecourseDao(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    private RacecourseDao getRacecourseDao() throws DalException {
        return unitOfWork.getRacecourseDao();
    }

    @Override
    public void create(Racecourse entity) throws DalException {
        getRacecourseDao().create(entity);
    }

    @Override
    public List<Racecourse> findByNamePart(String namePart, long offset, long limit) throws DalException {
        return getRacecourseDao().findByNamePart(namePart, offset, limit);
    }

    @Override
    public Racecourse find(Long id) throws DalException {
        return getRacecourseDao().find(id);
    }

    @Override
    public long findByNamePartCount(String namePart) throws DalException {
        return getRacecourseDao().findByNamePartCount(namePart);
    }

    @Override
    public List<Racecourse> findAll() throws DalException {
        return getRacecourseDao().findAll();
    }

    @Override
    public List<Racecourse> findAll(long offset, long limit) throws DalException {
        return getRacecourseDao().findAll(offset, limit);
    }

    @Override
    public long count() throws DalException {
        return getRacecourseDao().count();
    }

    @Override
    public long update(Racecourse entity) throws DalException {
        return getRacecourseDao().update(entity);
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return getRacecourseDao().delete(id);
    }
}
