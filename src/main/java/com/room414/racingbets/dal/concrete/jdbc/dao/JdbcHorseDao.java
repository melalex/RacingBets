package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.HorseDao;
import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.abstraction.exception.DalException;

import java.sql.Connection;
import java.util.List;

/**
 * Implementation of HorseDao that uses JDBC as data source.
 *
 * @see HorseDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcHorseDao implements HorseDao {
    private Connection connection;

    JdbcHorseDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Horse> findByNamePart(String namePart, long offset, long limit) {
        return null;
    }

    @Override
    public void create(Horse entity) throws DalException {

    }

    @Override
    public int findByNamePartCount(String namePart) {
        return 0;
    }

    @Override
    public Horse find(Long id) {
        return null;
    }

    @Override
    public List<Horse> findAll() throws DalException {
        return null;
    }

    @Override
    public List<Horse> findAll(long offset, long limit) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public long update(Horse entity) {
        return 0;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
