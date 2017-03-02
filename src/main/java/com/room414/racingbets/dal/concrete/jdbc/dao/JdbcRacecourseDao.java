package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.RacecourseDao;
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
    public boolean create(Racecourse entity) {

    }

    @Override
    public Racecourse find(Integer id) {
        return null;
    }

    @Override
    public List<Racecourse> findAll(int offset, int limit) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public int update(Racecourse entity) {

    }

    @Override
    public boolean delete(Integer id) {

    }

    @Override
    public List<Racecourse> findByNamePart(String namePart, int offset, int limit) {
        return null;
    }

    @Override
    public int findByNamePartCount(String namePart) {
        return 0;
    }
}
