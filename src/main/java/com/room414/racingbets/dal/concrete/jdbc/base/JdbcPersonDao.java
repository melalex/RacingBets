package com.room414.racingbets.dal.concrete.jdbc.base;

import com.room414.racingbets.dal.abstraction.dao.PersonDao;
import com.room414.racingbets.dal.abstraction.entities.Person;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.defaultErrorMessage;
import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.getResultList;
import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.startsWith;

/**
 * @author Alexander Melashchenko
 * @version 1.0 02 Mar 2017
 */
public abstract class JdbcPersonDao<T extends Person> implements PersonDao<T> {
    protected Connection connection;

    protected abstract String getTableName();
    protected abstract T mapResultSet(ResultSet resultSet);

    /**
     * Paste is not a cascading manner
     */
    @Override
    public void create(T entity) throws DalException {
        String sqlStatement = "INSERT INTO ? (first_name, last_name, birthday, country_id) VALUES (?, ?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, getTableName());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setDate(4, entity.getBirthday());
            statement.setLong(5, entity.getCountry().getId());

            JdbcDaoHelper.setId(statement, entity::setId);

        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
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
        String sqlStatement = "SELECT * FROM country WHERE name LIKE ? LIMIT ? OFFSET ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, startsWith(namePart));
            statement.setLong(2, limit);
            statement.setLong(3, offset);

            return getResultList(statement, this::mapResultSet);

        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, startsWith(namePart), limit, offset);
            throw new DalException(message, e);
        }
    }

    @Override
    public int findByNamePartCount(String namePart) {
        return 0;
    }

    @Override
    public T find(Long id) {
        return null;
    }

    @Override
    public List<T> findAll() throws DalException {
        return null;
    }

    @Override
    public List<T> findAll(long offset, long limit) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public long update(T entity) {
        return 0;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
