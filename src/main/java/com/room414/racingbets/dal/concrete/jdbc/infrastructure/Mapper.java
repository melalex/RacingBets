package com.room414.racingbets.dal.concrete.jdbc.infrastructure;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Alexander Melashchenko
 * @version 1.0 02 Mar 2017
 */
@FunctionalInterface
public interface Mapper<T> {
    T apply(ResultSet resultSet) throws SQLException;
}
