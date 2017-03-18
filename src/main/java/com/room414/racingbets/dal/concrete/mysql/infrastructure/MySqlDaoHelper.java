package com.room414.racingbets.dal.concrete.mysql.infrastructure;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Class that stores common methods for Dao (prevents code duplication)
 *
 * @author Alexander Melashchenko
 * @version 1.0 02 Mar 2017
 */
// TODO: is good place
// TODO: usage comments
public class MySqlDaoHelper {
    private final static String DEFAULT_ERROR_MESSAGE = "Exception during execution statement '%s'";
    private final static String CALL_ERROR_MESSAGE = "Exception during calling procedure '%s' ";

    private MySqlDaoHelper() {

    }

    private static String sqlFormat(String sqlPattern, Object ... arguments) {
        String sqlStatement = sqlPattern.replaceAll("\\?", "%s").replaceAll("\\s+", " ");
        return String.format(sqlStatement, arguments);
    }

    public static String defaultErrorMessage(String sqlPattern, Object ... arguments) {
        return String.format(DEFAULT_ERROR_MESSAGE, sqlFormat(sqlPattern, arguments));
    }

    public static String callErrorMessage(String procedureName, Object ... params) {
        String base = String.format(CALL_ERROR_MESSAGE, procedureName);

        if (params.length == 0) {
            return base;
        }

        StringBuilder builder = new StringBuilder(base);
        builder.append("with parameters: ");

        for (Object param : params) {
            builder.append(param.toString());
        }

        return builder.toString();
    }

    public static String startsWith(String part) {
        return part + "%";
    }

    static void setValues(PreparedStatement preparedStatement, Object... values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            preparedStatement.setObject(i + 1, values[i]);
        }
    }

    static long getCount(PreparedStatement statement) throws SQLException {
        return getResult(statement, MySqlMapHelper::mapCount);
    }

    static long executeUpdate(PreparedStatement statement) throws SQLException {
        return statement.executeUpdate();
    }

    static boolean execute(PreparedStatement statement) throws SQLException {
        return statement.execute();
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

    public static <T> T getResultWithArray(PreparedStatement statement, Mapper<List<T>> mapper) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            List<T> result = mapper.apply(resultSet);
            if (!result.isEmpty()) {
                return result.get(0);
            } else {
                return null;
            }
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

    public static <T> List<T> getResultListWithArray(PreparedStatement statement, Mapper<List<T>> mapper) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            return mapper.apply(resultSet);
        }
    }

    public static long createEntity(PreparedStatement statement, Consumer<Long> idSetter) throws SQLException {
        long affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
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

    public static void createEntities(PreparedStatement statement, List<Consumer<Long>> idSetters) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            int i = 0;
            while (generatedKeys.next()) {
                idSetters.get(i).accept(generatedKeys.getLong(1));
                i++;
            }
        }
    }
}
