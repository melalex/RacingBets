package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.CountryDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public boolean create(Country entity) throws DalException {
        String sqlStatement = "INSERT INTO country (name, code) VALUES (?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, entity.getName());
            statement.setString(1, entity.getCode());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 0) {

            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }


        } catch (SQLException e) {
            throw new DalException(e);
        }
    }

    @Override
    public Country find(Long id) {
        return null;
    }

    @Override
    public List<Country> findAll(long offset, long limit) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public long update(Country entity) {
        return 0;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List<Country> findByNamePart(String namePart, long offset, long limit) {
        return null;
    }

    @Override
    public int findByNamePartCount(String namePart) {
        return 0;
    }
}
