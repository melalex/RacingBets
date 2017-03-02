package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.OwnerDao;
import com.room414.racingbets.dal.concrete.jdbc.base.JdbcPersonDao;
import com.room414.racingbets.dal.domain.entities.Owner;

import java.sql.Connection;
import java.util.List;

/**
 * Implementation of OwnerDao that uses JDBC as data source.
 *
 * @see OwnerDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcOwnerDao extends JdbcPersonDao<Owner> implements OwnerDao {
    Connection connection;

    JdbcOwnerDao(Connection connection) {
        this.connection = connection;
    }


}
