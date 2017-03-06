package com.room414.racingbets.dal.concrete.mysql.infrastructure;

import com.room414.racingbets.dal.abstraction.exception.DalException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 03 Mar 2017
 */
public class MySqlFindByColumnExecutor<T> extends MySqlCrudExecutor<T> {

    public MySqlFindByColumnExecutor(Connection connection, Mapper<T> mapper) {
        super(connection, mapper);
    }

    public List<T> findByColumnPart(String sqlStatement, String namePart, long offset, long limit) throws DalException {
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, startsWith(namePart));
            statement.setLong(2, limit);
            statement.setLong(3, offset);

            return getResultList(statement, mapper);

        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, startsWith(namePart), limit, offset);
            throw new DalException(message, e);
        }
    }

    public long findByColumnPartCount(String sqlStatement, String namePart) throws DalException {
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, startsWith(namePart));

            return getResult(statement, MySqlMapHelper::mapCount);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, startsWith(namePart));
            throw new DalException(message, e);
        }
    }
}
