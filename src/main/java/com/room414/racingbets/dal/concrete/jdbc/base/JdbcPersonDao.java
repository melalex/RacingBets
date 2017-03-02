package com.room414.racingbets.dal.concrete.jdbc.base;

import com.room414.racingbets.dal.abstraction.dao.PersonDao;
import com.room414.racingbets.dal.abstraction.entities.Person;
import com.room414.racingbets.dal.abstraction.exception.DalException;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 02 Mar 2017
 */
public abstract class JdbcPersonDao<T extends Person> implements PersonDao<T> {
    @Override
    public boolean create(T entity) throws DalException {
        return false;
    }

    @Override
    public List<T> findByNamePart(String namePart, long offset, long limit) {
        return null;
    }

    @Override
    public int findByNamePartCount(String namePart) {
        return 0;
    }

    @Override
    public T find(Long id) {
        return null;
    }

    @Override
    public List<T> findAll(long offset, long limit) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public long update(T entity) {
        return 0;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
