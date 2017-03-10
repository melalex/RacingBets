package com.room414.racingbets.dal.concrete.mysql.infrastructure;

import java.sql.PreparedStatement;

/**
 * @author melalex
 * @version 1.0 10 Mar 2017
 */
@FunctionalInterface
public interface QueryExecutor<T> {
    T apply(PreparedStatement statement);
}
