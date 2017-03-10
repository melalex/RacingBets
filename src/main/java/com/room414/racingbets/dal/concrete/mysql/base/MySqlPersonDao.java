package com.room414.racingbets.dal.concrete.mysql.base;

import com.room414.racingbets.dal.abstraction.dao.PersonDao;
import com.room414.racingbets.dal.abstraction.entities.Person;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlMapHelper;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlSharedExecutor;

import java.sql.*;
import java.util.List;

import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 02 Mar 2017
 */
// TODO: cascading comment
// TODO: sql injection
public abstract class MySqlPersonDao<T extends Person> implements PersonDao<T> {
    private Connection connection;
    private MySqlSharedExecutor<T> executor;

    public MySqlPersonDao(Connection connection) {
        this.connection = connection;
        this.executor = new MySqlSharedExecutor<>(
                connection,
                statement -> MySqlDaoHelper.getResult(statement, this::mapResultSet),
                statement -> MySqlDaoHelper.getResultList(statement, this::mapResultSet)
        );
    }

    protected abstract String getTableName();

    protected abstract T mapResultSet(ResultSet resultSet) throws SQLException;

    @Override
    public void create(T entity) throws DalException {
        String sqlStatement = String.format(
                "INSERT INTO %s (first_name, last_name, birthday) VALUES (?, ?, ?)",
                getTableName()
        );

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setDate(3, entity.getBirthday());

            MySqlDaoHelper.createEntity(statement, entity::setId);

        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getBirthday()
            );
            throw new DalException(message, e);
        }
    }

    @Override
    public List<T> findByNamePart(String namePart, long offset, long limit) throws DalException {
        String sqlStatement = String.format(
                "SELECT * FROM %s WHERE first_name LIKE ? OR last_name LIKE ? LIMIT ? OFFSET ?",
                getTableName()
        );

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, startsWith(namePart));
            statement.setString(2, startsWith(namePart));
            statement.setLong(3, limit);
            statement.setLong(4, offset);

            return getResultList(statement, this::mapResultSet);
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
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
        String sqlStatement = String.format(
                "SELECT COUNT(*) as count FROM %s WHERE first_name LIKE ? OR last_name LIKE ?",
                getTableName()
        );

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, startsWith(namePart));
            statement.setString(2, startsWith(namePart));

            return getResult(statement, MySqlMapHelper::mapCount);
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    startsWith(namePart),
                    startsWith(namePart)
            );
            throw new DalException(message, e);
        }
    }

    @Override
    public T find(Long id) throws DalException {
        String sqlStatement = String.format(
                "SELECT * FROM %s WHERE id = ?",
                getTableName()
        );

        return executor.find(id, sqlStatement);
    }

    @Override
    public List<T> findAll() throws DalException {
        String sqlStatement = String.format("SELECT * FROM %s", getTableName());

        return executor.findAll(sqlStatement);
    }

    @Override
    public List<T> findAll(long offset, long limit) throws DalException {
        String sqlStatement = String.format(
                "SELECT * FROM %s LIMIT ? OFFSET ?", getTableName()
        );

        return executor.findAll(sqlStatement, limit, offset);
    }

    @Override
    public long count() throws DalException {
        return executor.count(getTableName());
    }

    @Override
    public long update(T entity) throws DalException {
        String sqlStatement = String.format(
                "UPDATE %s SET first_name = ?, last_name = ?, birthday = ? WHERE id = ?", getTableName()
        );

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setDate(3, entity.getBirthday());
            statement.setLong(4, entity.getId());

            return statement.executeUpdate();
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
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
        return executor.delete(getTableName(), id);
    }
}
