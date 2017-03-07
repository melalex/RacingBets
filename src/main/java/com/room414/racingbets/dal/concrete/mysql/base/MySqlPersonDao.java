package com.room414.racingbets.dal.concrete.mysql.base;

import com.room414.racingbets.dal.abstraction.dao.PersonDao;
import com.room414.racingbets.dal.abstraction.entities.Person;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlMapHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 02 Mar 2017
 */
// TODO: cascading comment
public abstract class MySqlPersonDao<T extends Person> implements PersonDao<T> {
    protected Connection connection;

    protected abstract String getTableName();

    protected abstract T mapResultSet(ResultSet resultSet) throws SQLException;

    @Override
    public void create(T entity) throws DalException {
        String sqlStatement = "INSERT INTO ? (first_name, last_name, birthday) VALUES (?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, getTableName());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setDate(4, entity.getBirthday());

            MySqlDaoHelper.createEntity(statement, entity::setId);

        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    getTableName(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getBirthday()
            );
            throw new DalException(message, e);
        }
    }

    @Override
    public List<T> findByNamePart(String namePart, long offset, long limit) throws DalException {
        String sqlStatement = "SELECT * FROM ? WHERE first_name LIKE ? OR last_name LIKE ? LIMIT ? OFFSET ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, getTableName());
            statement.setString(2, startsWith(namePart));
            statement.setString(3, startsWith(namePart));
            statement.setLong(4, limit);
            statement.setLong(5, offset);

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
        String sqlStatement = "SELECT COUNT(*) FROM ? WHERE first_name LIKE ? OR last_name LIKE ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, getTableName());
            statement.setString(2, startsWith(namePart));
            statement.setString(3, startsWith(namePart));

            return getResult(statement, MySqlMapHelper::mapCount);
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
        String sqlStatement = "SELECT * FROM ? WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, getTableName());
            statement.setLong(2, id);

            return getResult(statement, this::mapResultSet);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, getTableName(), id);
            throw new DalException(message, e);
        }
    }

    @Override
    public List<T> findAll() throws DalException {
        String sqlStatement = "SELECT * FROM ? ";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, getTableName());

            return getResultList(statement, this::mapResultSet);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, getTableName(), getTableName());
            throw new DalException(message, e);
        }    }

    @Override
    public List<T> findAll(long offset, long limit) throws DalException {
        String sqlStatement = "SELECT * FROM ? LIMIT ? OFFSET ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, getTableName());
            statement.setLong(2, limit);
            statement.setLong(2, offset);

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

            return getResult(statement, MySqlMapHelper::mapCount);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, getTableName());
            throw new DalException(message, e);
        }
    }

    @Override
    public long update(T entity) throws DalException {
        String sqlStatement = "UPDATE ? SET first_name = ?, last_name = ?, birthday = ? WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, getTableName());

            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setDate(4, entity.getBirthday());

            return statement.executeUpdate();
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    getTableName(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getBirthday(),
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
