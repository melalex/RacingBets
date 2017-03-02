package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.JockeyDao;
import com.room414.racingbets.dal.domain.entities.Jockey;

import java.sql.Connection;
import java.util.List;

/**
 * Implementation of JockeyDao that uses JDBC as data source.
 *
 * @see JockeyDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcJockeyDao implements JockeyDao {
    private Connection connection;

    JdbcJockeyDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean create(Jockey entity) {

    }

    @Override
    public Jockey find(Integer id) {
        return null;
    }

    @Override
    public List<Jockey> findAll(int offset, int limit) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public int update(Jockey entity) {

    }

    @Override
    public boolean delete(Integer id) {

    }

    @Override
    public List<Jockey> findByNamePart(String namePart, int offset, int limit) {
        return null;
    }

    @Override
    public int findByNamePartCount(String namePart) {
        return 0;
    }
}
