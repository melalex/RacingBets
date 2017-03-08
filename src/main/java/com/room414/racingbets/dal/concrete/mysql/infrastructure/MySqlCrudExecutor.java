package com.room414.racingbets.dal.concrete.mysql.infrastructure;

import com.room414.racingbets.dal.abstraction.exception.DalException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.defaultErrorMessage;
import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.getResult;
import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.getResultList;

/**
 * @author Alexander Melashchenko
 * @version 1.0 03 Mar 2017
 */
public class MySqlCrudExecutor<T> extends MySqlSimpleQueryExecutor {
    protected Mapper<T> mapper;

    public MySqlCrudExecutor(Connection connection, Mapper<T> mapper) {
        super(connection);
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

    public List<T> findAll(String sqlStatement) throws DalException {
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            return getResultList(statement, mapper);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement);
            throw new DalException(message, e);
        }
    }

    public List<T> findAll(String sqlStatement, long limit, long offset) throws DalException {
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, limit);
            statement.setLong(2, offset);

            return getResultList(statement, mapper);
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

            return getResultList(statement, mapper);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, key, limit, offset);
            throw new DalException(message, e);
        }
    }
}
