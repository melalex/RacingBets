package com.room414.racingbets.dal.concrete.jdbc.infrastructure;

import com.room414.racingbets.dal.abstraction.exception.DalException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.defaultErrorMessage;
import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.getResult;
import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.getResultList;

/**
 * @author Alexander Melashchenko
 * @version 1.0 03 Mar 2017
 */
public class JdbcCrudExecutor<T> {
    protected Connection connection;
    protected Mapper<T> mapper;

    public JdbcCrudExecutor(Connection connection, Mapper<T> mapper) {
        this.connection = connection;
        this.mapper = mapper;
    }

    public T find(long id, String sqlStatement) throws DalException {
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, id);

            return getResult(statement, mapper);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, id);
            throw new DalException(message, e);
        }
    }

    public List<T> findAll(String tableName) throws DalException {
        String sqlStatement = "SELECT * FROM ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, tableName);

            return getResultList(statement, mapper);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, tableName);
            throw new DalException(message, e);
        }
    }

    public List<T> findAll(String tableName, long limit, long offset) throws DalException {
        String sqlStatement = "SELECT * FROM ? LIMIT ? OFFSET ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, tableName);
            statement.setLong(2, limit);
            statement.setLong(3, offset);

            return getResultList(statement, mapper);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, tableName, limit, offset);
            throw new DalException(message, e);
        }
    }

    public long count(String tableName) throws DalException {
        String sqlStatement = "SELECT COUNT(*) AS count FROM ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, tableName);

            return getResult(statement, JdbcMapHelper::mapCount);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, tableName);
            throw new DalException(message, e);
        }
    }

    public boolean delete(String tableName, Long id) throws DalException {
        String sqlStatement = "DELETE FROM ? WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, tableName);
            statement.setLong(2, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, id);
            throw new DalException(message, e);
        }
    }
}
