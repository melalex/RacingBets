package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.CountryDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper;
import com.room414.racingbets.dal.domain.entities.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    // TODO: is should be local
    private final String ID_COLUMN = "country.id";
    private final String NAME_COLUMN = "country.name";
    private final String CODE_COLUMN = "country.code";

    private Connection connection;

    JdbcCountryDao(Connection connection) {
        this.connection = connection;
    }


    private Country countryFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID_COLUMN);
        String name = resultSet.getString(NAME_COLUMN);
        String code = resultSet.getString(CODE_COLUMN);
        return new Country(id, name, code);
    }

    @Override
    public void create(Country entity) throws DalException {
        String sqlStatement = "INSERT INTO country (name, code) VALUES (?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getCode());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, entity.getName(), entity.getCode());
            throw new DalException(message, e);
        }
    }

    @Override
    public Country find(Long id) throws DalException {
        String sqlStatement = "SELECT * FROM country WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, id);

            return getResult(statement, this::countryFromResultSet);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, id);
            throw new DalException(message, e);
        }
    }

    @Override
    public List<Country> findAll() throws DalException {
        String sqlStatement = "SELECT * FROM country";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            return getResultList(statement, this::countryFromResultSet);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement);
            throw new DalException(message, e);
        }
    }

    @Override
    public List<Country> findAll(long offset, long limit) throws DalException {
        String sqlStatement = "SELECT * FROM country LIMIT ? OFFSET ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, limit);
            statement.setLong(2, offset);

            return getResultList(statement, this::countryFromResultSet);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, limit, offset);
            throw new DalException(message, e);
        }
    }

    @Override
    public long count() throws DalException {
        String sqlStatement = "SELECT COUNT(*) AS count FROM country";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            return getResult(statement, JdbcDaoHelper::countMapper);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement);
            throw new DalException(message, e);
        }
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
        String sqlStatement = "DELETE FROM country WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, id);
            throw new DalException(message, e);
        }
    }

    @Override
    public List<Country> findByNamePart(String namePart, long offset, long limit) throws DalException {
        String sqlStatement = "SELECT * FROM country WHERE name LIKE ? LIMIT ? OFFSET ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, startsWith(namePart));
            statement.setLong(2, limit);
            statement.setLong(3, offset);

            return getResultList(statement, this::countryFromResultSet);

        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, startsWith(namePart), limit, offset);
            throw new DalException(message, e);
        }
    }

    @Override
    public long findByNamePartCount(String namePart) throws DalException {
        String sqlStatement = "SELECT COUNT(*) AS count FROM country WHERE name LIKE ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, startsWith(namePart));

            return getResult(statement, JdbcDaoHelper::countMapper);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, startsWith(namePart));
            throw new DalException(message, e);
        }
    }
}
