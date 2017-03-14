package com.room414.racingbets.dal.concrete.caching.infrastructure.lazyload;

import com.room414.racingbets.dal.abstraction.dao.OwnerDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Owner;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class LazyLoadOwnerDao implements OwnerDao {
    private UnitOfWork unitOfWork;

    public LazyLoadOwnerDao(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    private OwnerDao getOwnerDao() throws DalException {
        return unitOfWork.getOwnerDao();
    }

    @Override
    public void create(Owner entity) throws DalException {
        getOwnerDao().create(entity);
    }

    @Override
    public List<Owner> findByNamePart(String namePart, long offset, long limit) throws DalException {
        return getOwnerDao().findByNamePart(namePart, offset, limit);
    }

    @Override
    public Owner find(Long id) throws DalException {
        return getOwnerDao().find(id);
    }

    @Override
    public long findByNamePartCount(String namePart) throws DalException {
        return getOwnerDao().findByNamePartCount(namePart);
    }

    @Override
    public List<Owner> findAll() throws DalException {
        return getOwnerDao().findAll();
    }

    @Override
    public List<Owner> findAll(long offset, long limit) throws DalException {
        return getOwnerDao().findAll(offset, limit);
    }

    @Override
    public long count() throws DalException {
        return getOwnerDao().count();
    }

    @Override
    public long update(Owner entity) throws DalException {
        return getOwnerDao().update(entity);
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return getOwnerDao().delete(id);
    }
}
