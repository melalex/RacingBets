package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.HorseDao;
import com.room414.racingbets.dal.abstraction.entities.Horse;

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
    public boolean create(Horse entity) {

    }

    @Override
    public Horse find(Integer id) {
        return null;
    }

    @Override
    public List<Horse> findAll(int offset, int limit) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public int update(Horse entity) {

    }

    @Override
    public boolean delete(Integer id) {

    }

    @Override
    public List<Horse> findByNamePart(String namePart, int offset, int limit) {
        return null;
    }

    @Override
    public int findByNamePartCount(String namePart) {
        return 0;
    }
}
