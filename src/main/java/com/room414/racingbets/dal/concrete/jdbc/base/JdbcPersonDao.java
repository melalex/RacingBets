package com.room414.racingbets.dal.concrete.jdbc.base;

import com.room414.racingbets.dal.abstraction.dao.PersonDao;
import com.room414.racingbets.dal.abstraction.entities.Person;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcMapHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 02 Mar 2017
 */
// TODO: cascading comment
public abstract class JdbcPersonDao<T extends Person> implements PersonDao<T> {
    protected Connection connection;

    protected abstract String getTableName();

    protected abstract T mapResultSet(ResultSet resultSet) throws SQLException;

    @Override
    public void create(T entity) throws DalException {
        String sqlStatement = "INSERT INTO ? (first_name, last_name, birthday, country_id) VALUES (?, ?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, getTableName());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setDate(4, entity.getBirthday());
            statement.setLong(5, entity.getCountry().getId());

            JdbcDaoHelper.createEntity(statement, entity::setId);

        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    getTableName(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getBirthday(),
                    entity.getCountry().getId()
            );
            throw new DalException(message, e);
        }
    }

    @Override
    public List<T> findByNamePart(String namePart, long offset, long limit) throws DalException {
        String sqlStatement = "SELECT * FROM ? " +
                "INNER JOIN country ON country.id = ?.country_id " +
                "WHERE first_name LIKE ? OR last_name LIKE ? LIMIT ? OFFSET ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, getTableName());
            statement.setString(2, getTableName());
            statement.setString(3, startsWith(namePart));
            statement.setString(4, startsWith(namePart));
            statement.setLong(5, limit);
            statement.setLong(6, offset);

            return getResultList(statement, this::mapResultSet);
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    getTableName(),
                    getTableName(),
                    startsWith(namePart),
                    startsWith(namePart),
                    limit,
                    offset
            );
            throw new DalException(message, e);
        }
    }

    @Override
    public long findByNamePartCount(String namePart) throws DalException {
        String sqlStatement = "SELECT COUNT(*) FROM ? " +
                "WHERE first_name LIKE ? OR last_name LIKE ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, getTableName());
            statement.setString(2, startsWith(namePart));
            statement.setString(3, startsWith(namePart));

            return getResult(statement, JdbcMapHelper::mapCount);
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    getTableName(),
                    startsWith(namePart),
                    startsWith(namePart)
            );
            throw new DalException(message, e);
        }
    }

    @Override
    public T find(Long id) throws DalException {
        String sqlStatement = "SELECT * FROM ? " +
                "INNER JOIN country ON country.id = ?.country_id " +
                "WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, getTableName());
            statement.setString(2, getTableName());
            statement.setLong(3, id);

            return getResult(statement, this::mapResultSet);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, getTableName(), getTableName(), id);
            throw new DalException(message, e);
        }
    }

    @Override
    public List<T> findAll() throws DalException {
        String sqlStatement = "SELECT * FROM ? " +
                "INNER JOIN country ON country.id = ?.country_id";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, getTableName());
            statement.setString(2, getTableName());

            return getResultList(statement, this::mapResultSet);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, getTableName(), getTableName());
            throw new DalException(message, e);
        }    }

    @Override
    public List<T> findAll(long offset, long limit) throws DalException {
        String sqlStatement = "SELECT * FROM ? " +
                "INNER JOIN country ON country.id = ?.country_id " +
                "LIMIT ? OFFSET ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, getTableName());
            statement.setString(2, getTableName());
            statement.setLong(3, limit);
            statement.setLong(4, offset);

            return getResultList(statement, this::mapResultSet);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, getTableName(), getTableName(), limit, offset);
            throw new DalException(message, e);
        }
    }

    @Override
    public long count() throws DalException {
        String sqlStatement = "SELECT COUNT(*) AS count FROM ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, getTableName());

            return getResult(statement, JdbcMapHelper::mapCount);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, getTableName());
            throw new DalException(message, e);
        }
    }

    @Override
    public long update(T entity) throws DalException {
        String sqlStatement = "UPDATE ? SET first_name = ?, last_name = ?, birthday = ?, country_id = ? WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, getTableName());

            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setDate(4, entity.getBirthday());
            statement.setLong(5, entity.getCountry().getId());

            statement.setLong(6, entity.getId());


            return statement.executeUpdate();
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    getTableName(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getBirthday(),
                    entity.getCountry().getId(),
                    entity.getId()
            );
            throw new DalException(message, e);
        }
    }

    @Override
    public boolean delete(Long id) throws DalException {
        String sqlStatement = "DELETE FROM ? WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, getTableName());
            statement.setLong(2, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, getTableName(), id);
            throw new DalException(message, e);
        }
    }
}
