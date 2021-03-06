package com.room414.racingbets.dal.concrete.caching.infrastructure.lazyload;

import com.room414.racingbets.dal.abstraction.dao.OwnerDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
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

    private OwnerDao getOwnerDao() {
        return unitOfWork.getOwnerDao();
    }

    @Override
    public void create(Owner entity) {
        getOwnerDao().create(entity);
    }

    @Override
    public List<Owner> search(String namePart, int offset, int limit) {
        return getOwnerDao().search(namePart, offset, limit);
    }

    @Override
    public Owner find(Long id) {
        return getOwnerDao().find(id);
    }

    @Override
    public int searchCount(String namePart) {
        return getOwnerDao().searchCount(namePart);
    }

    @Override
    public List<Owner> findAll() {
        return getOwnerDao().findAll();
    }

    @Override
    public List<Owner> findAll(int offset, int limit) {
        return getOwnerDao().findAll(offset, limit);
    }

    @Override
    public int count() {
        return getOwnerDao().count();
    }

    @Override
    public long update(Owner entity) {
        return getOwnerDao().update(entity);
    }

    @Override
    public boolean delete(Long id) {
        return getOwnerDao().delete(id);
    }
}
