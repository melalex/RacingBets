package com.room414.racingbets.dal.concrete.mysql.infrastructure;

import com.room414.racingbets.dal.abstraction.exception.DalException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.defaultErrorMessage;
import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.getResult;

/**
 * @author Alexander Melashchenko
 * @version 1.0 06 Mar 2017
 */
// TODO: comment about sql injection
public class MySqlSimpleQueryExecutor {
    protected Connection connection;

    public MySqlSimpleQueryExecutor(Connection connection) {
        this.connection = connection;
    }

    public long count(String tableName) throws DalException {
        String sqlStatement = String.format("SELECT COUNT(*) AS count FROM %s", tableName);

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            return getResult(statement, MySqlMapHelper::mapCount);
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

    public long findByForeignKeyCount(String tableName, String columnName, long key) throws DalException {
        final String sqlStatement = String.format(
                "SELECT Count(*) AS count FROM %s WHERE %s = ?", tableName, columnName
        );

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, key);

            return getResult(statement, MySqlMapHelper::mapCount);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, key);
            throw new DalException(message, e);
        }
    }
}
