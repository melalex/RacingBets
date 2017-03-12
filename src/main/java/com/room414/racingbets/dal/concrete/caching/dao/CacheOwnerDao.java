package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.dao.OwnerDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Owner;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CacheOwnerDao implements OwnerDao {
    private OwnerDao ownerDao;

    public CacheOwnerDao(OwnerDao ownerDao) {
        this.ownerDao = ownerDao;
    }

    @Override
    public void create(Owner entity) throws DalException {
        ownerDao.create(entity);
    }

    @Override
    public List<Owner> findByNamePart(String namePart, long offset, long limit) throws DalException {
        return ownerDao.findByNamePart(namePart, offset, limit);
    }

    @Override
    public Owner find(Long id) throws DalException {
        return ownerDao.find(id);
    }

    @Override
    public long findByNamePartCount(String namePart) throws DalException {
        return ownerDao.findByNamePartCount(namePart);
    }

    @Override
    public List<Owner> findAll() throws DalException {
        return ownerDao.findAll();
    }

    @Override
    public List<Owner> findAll(long offset, long limit) throws DalException {
        return ownerDao.findAll(offset, limit);
    }

    @Override
    public long count() throws DalException {
        return ownerDao.count();
    }

    @Override
    public long update(Owner entity) throws DalException {
        return ownerDao.update(entity);
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return ownerDao.delete(id);
    }
}
