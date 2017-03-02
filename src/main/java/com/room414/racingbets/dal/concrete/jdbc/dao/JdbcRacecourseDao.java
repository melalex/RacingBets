package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.RacecourseDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Racecourse;

import java.sql.Connection;
import java.util.List;

/**
 * Implementation of RacecourseDao that uses JDBC as data source.
 *
 * @see RacecourseDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcRacecourseDao implements RacecourseDao {
    Connection connection;

    JdbcRacecourseDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Racecourse> findByNamePart(String namePart, long offset, long limit) {
        return null;
    }

    @Override
    public int findByNamePartCount(String namePart) {
        return 0;
    }

    @Override
    public boolean create(Racecourse entity) throws DalException {
        return false;
    }

    @Override
    public Racecourse find(Long id) {
        return null;
    }

    @Override
    public List<Racecourse> findAll(long offset, long limit) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public long update(Racecourse entity) {
        return 0;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
