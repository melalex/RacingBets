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

    public long count(String tableName) {
        String sqlStatement = String.format("SELECT COUNT(*) AS count FROM %s", tableName);

        return executeCountQuery(sqlStatement);
    }

    public boolean delete(String tableName, Long id) {
        String sqlStatement = String.format("DELETE FROM %s WHERE id = ?", tableName);

        return executeUpdateQuery(sqlStatement, id) > 0;
    }

    public T find(long id, String sqlStatement) {
        return executeFindOneQuery(sqlStatement, id);
    }

    public List<T> findAll(String sqlStatement) {
        return executeFindManyQuery(sqlStatement);
    }

    public List<T> findAll(String sqlStatement, long limit, long offset) {
        return executeFindManyQuery(sqlStatement, limit, offset);
    }

    public List<T> findByForeignKey(String sqlStatement, long key, long offset, long limit) {
        return executeFindManyQuery(sqlStatement, key, limit, offset);
    }

    public long findByForeignKeyCount(String tableName, String columnName, long key) {
        final String sqlStatement = String.format(
                "SELECT Count(*) AS count FROM %s WHERE %s = ?", tableName, columnName
        );

        return executeCountQuery(sqlStatement, key);
    }

    public List<T> findByColumnPart(String sqlStatement, String namePart, long limit, long offset) {
        return executeFindManyQuery(sqlStatement, startsWith(namePart), limit, offset);
    }

    public long findByColumnPartCount(String sqlStatement, String namePart) {
        return executeCountQuery(sqlStatement, startsWith(namePart));
    }

    public long executeUpdateQuery(String sqlStatement, Object ... objects) {
        return executeQuery(MySqlDaoHelper::executeUpdate, sqlStatement, objects);
    }

    public long executeCountQuery(String sqlStatement, Object ... objects) {
        return executeQuery(MySqlDaoHelper::getCount, sqlStatement, objects);
    }

    public T executeFindOneQuery(String sqlStatement, Object ... objects) {
        return executeQuery(mapResult, sqlStatement, objects);
    }

    public List<T> executeFindManyQuery(String sqlStatement, Object ... objects) {
        return executeQuery(mapResultList, sqlStatement, objects);
    }

    private <R> R executeQuery(QueryExecutor<R> queryExecutor, String sqlStatement, Object ... objects) {
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            setValues(statement, objects);
            return queryExecutor.execute(statement);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, objects);
            throw new DalException(message, e);
        }
    }
}
