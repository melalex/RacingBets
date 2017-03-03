package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.CountryDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcFindByColumnExecutor;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcMapHelper;
import com.room414.racingbets.dal.domain.entities.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.*;

/**
 * Implementation of CountryDao that uses JDBC as data source.
 *
 * @see CountryDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
// TODO: queries as constants
public class JdbcCountryDao implements CountryDao {
    private static String TABLE_NAME = "country";

    private Connection connection;
    private JdbcFindByColumnExecutor<Country> executor;

    JdbcCountryDao(Connection connection) {
        this.connection = connection;
        this.executor = new JdbcFindByColumnExecutor<>(connection, JdbcMapHelper::mapCountry);
    }

    @Override
    public void create(Country entity) throws DalException {
        String sqlStatement = "INSERT INTO country (name, code) VALUES (?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getCode());

            createEntity(statement, entity::setId);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, entity.getName(), entity.getCode());
            throw new DalException(message, e);
        }
    }

    @Override
    public Country find(Long id) throws DalException {
        String sqlStatement = "SELECT * FROM country WHERE id = ?";

        return executor.find(id, sqlStatement);
    }

    @Override
    public List<Country> findAll() throws DalException {
        String sqlStatement = "SELECT * FROM country";

        return executor.findAll(sqlStatement);
    }

    @Override
    public List<Country> findAll(long offset, long limit) throws DalException {
        String sqlStatement = "SELECT * FROM country LIMIT ? OFFSET ?";

        return executor.findAll(sqlStatement, limit, offset);
    }

    @Override
    public long count() throws DalException {
        return executor.count(TABLE_NAME);
    }

    @Override
    public long update(Country entity) throws DalException {
        String sqlStatement = "UPDATE country SET name = ?, code = ? WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getCode());
            statement.setLong(3, entity.getId());

            return statement.executeUpdate();
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    entity.getName(),
                    entity.getCode(),
                    entity.getId()
            );
            throw new DalException(message, e);
        }
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return executor.delete(TABLE_NAME, id);
    }

    @Override
    public List<Country> findByNamePart(String namePart, long offset, long limit) throws DalException {
        String sqlStatement = "SELECT * FROM country WHERE name LIKE ? LIMIT ? OFFSET ?";

        return executor.findByColumnPart(sqlStatement, namePart, offset, limit);
    }

    @Override
    public long findByNamePartCount(String namePart) throws DalException {
        String sqlStatement = "SELECT COUNT(*) AS count FROM country WHERE name LIKE ?";

        return executor.findByColumnPartCount(sqlStatement, namePart);
    }
}
