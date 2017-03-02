package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.CountryDao;
import com.room414.racingbets.dal.domain.entities.Country;

import java.sql.Connection;
import java.util.List;

/**
 * Implementation of CountryDao that uses JDBC as data source.
 *
 * @see CountryDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcCountryDao implements CountryDao {
    private Connection connection;

    JdbcCountryDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void create(Country entity) {

    }

    @Override
    public Country find(Integer id) {
        return null;
    }

    @Override
    public List<Country> findAll(int offset, int limit) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public void update(Country entity) {

    }

    @Override
    public void delete(Integer id) {

    }
}
