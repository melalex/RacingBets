package com.room414.racingbets.dal.concrete.jdbc.infrastructure;

import com.room414.racingbets.dal.abstraction.exception.DalException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.defaultErrorMessage;
import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.getResult;

/**
 * @author Alexander Melashchenko
 * @version 1.0 06 Mar 2017
 */
public class JdbcSimpleQueryExecutor {
    protected Connection connection;

    public JdbcSimpleQueryExecutor(Connection connection) {
        this.connection = connection;
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


    public long findByForeignKeyCount(String tableName, String columnName, long key) throws DalException {
        final String sqlStatement = "SELECT Count(*) FROM ? WHERE ? = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, tableName);
            statement.setString(2, columnName);
            statement.setLong(3, key);

            return getResult(statement, JdbcMapHelper::mapCount);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, key);
            throw new DalException(message, e);
        }
    }
}
