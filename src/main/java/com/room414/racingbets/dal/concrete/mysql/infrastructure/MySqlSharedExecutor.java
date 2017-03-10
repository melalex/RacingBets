package com.room414.racingbets.dal.concrete.mysql.infrastructure;

import com.room414.racingbets.dal.abstraction.exception.DalException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.*;
import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.startsWith;

/**
 * Class that stores common methods for query executing (prevents code duplication)
 *
 * @author Alexander Melashchenko
 * @version 1.0 03 Mar 2017
 */
public class MySqlSharedExecutor<T> {
    private Connection connection;
    private QueryExecutor<T> mapResult;
    private QueryExecutor<List<T>> mapResultList;

    public MySqlSharedExecutor(Connection connection, QueryExecutor<T> mapResult, QueryExecutor<List<T>> mapResultList) {
        this.connection = connection;
        this.mapResult = mapResult;
        this.mapResultList = mapResultList;
    }

    public long count(String tableName) throws DalException {
        String sqlStatement = String.format("SELECT COUNT(*) AS count FROM %s", tableName);

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            return getCount(statement);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement);
            throw new DalException(message, e);
        }
    }

    public boolean delete(String tableName, Long id) throws DalException {
        String sqlStatement = String.format("DELETE FROM %s WHERE id = ?", tableName);

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, id);
            throw new DalException(message, e);
        }
    }

    public T find(long id, String sqlStatement) throws DalException {
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, id);
            return mapResult.apply(statement);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, id);
            throw new DalException(message, e);
        }
    }

    public List<T> findAll(String sqlStatement) throws DalException {
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            return mapResultList.apply(statement);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement);
            throw new DalException(message, e);
        }
    }

    public List<T> findAll(String sqlStatement, long limit, long offset) throws DalException {
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, limit);
            statement.setLong(2, offset);

            return mapResultList.apply(statement);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, limit, offset);
            throw new DalException(message, e);
        }
    }

    public List<T> findByForeignKey(String sqlStatement, long key, long offset, long limit) throws DalException {
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, key);
            statement.setLong(2, limit);
            statement.setLong(3, offset);

            return mapResultList.apply(statement);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, key, limit, offset);
            throw new DalException(message, e);
        }
    }

    public long findByForeignKeyCount(String tableName, String columnName, long key) throws DalException {
        final String sqlStatement = String.format(
                "SELECT Count(*) AS count FROM %s WHERE %s = ?", tableName, columnName
        );

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, key);
            return getCount(statement);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, key);
            throw new DalException(message, e);
        }
    }

    public List<T> findByColumnPart(String sqlStatement, String namePart, long limit, long offset) throws DalException {
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, startsWith(namePart));
            statement.setLong(2, limit);
            statement.setLong(3, offset);

            return mapResultList.apply(statement);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, startsWith(namePart), limit, offset);
            throw new DalException(message, e);
        }
    }

    public long findByColumnPartCount(String sqlStatement, String namePart) throws DalException {
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, startsWith(namePart));

            return getCount(statement);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, startsWith(namePart));
            throw new DalException(message, e);
        }
    }

}
