package com.room414.racingbets.dal.concrete.mysql.infrastructure;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Alexander Melashchenko
 * @version 1.0 02 Mar 2017
 */
// TODO: is good place
// TODO: usage comments
public class MySqlDaoHelper {
    private final static String DEFAULT_ERROR_MESSAGE = "Exception during execution statement '%s'";

    private MySqlDaoHelper() {

    }

    public static String sqlFormat(String sqlPattern, Object ... arguments) {
        String sqlStatement = sqlPattern.replaceAll("\\?", "%s");
        return String.format(sqlStatement, arguments);
    }

    public static String defaultErrorMessage(String sqlPattern, Object ... arguments) {
        return String.format(DEFAULT_ERROR_MESSAGE, sqlFormat(sqlPattern, arguments));
    }

    public static String startsWith(String part) {
        return part + "%";
    }

    public static <T> T getResult(PreparedStatement statement, Mapper<T> mapper) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            T result = null;

            if (resultSet.next()) {
                result = mapper.apply(resultSet);
            }

            return result;
        }
    }

    public static <T> List<T> getResultList(PreparedStatement statement, Mapper<T> mapper) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            List<T> result = new LinkedList<>();

            while (resultSet.next()) {
                result.add(mapper.apply(resultSet));
            }

            return result;
        }
    }

    public static long createEntity(PreparedStatement statement, Consumer<Long> idSetter) throws SQLException {
        long affectedRows = statement.executeUpdate();

        if (affectedRows != 0) {
            throw new SQLException("Creating entity failed, no rows affected.");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                idSetter.accept(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Creating entity failed, no ID obtained.");
            }
        }

        return affectedRows;
    }
}
