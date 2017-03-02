package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.OwnerDao;
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
public class JdbcOwnerDao implements OwnerDao {
    Connection connection;

    JdbcOwnerDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean create(Owner entity) {

    }

    @Override
    public Owner find(Integer id) {
        return null;
    }

    @Override
    public List<Owner> findAll(int offset, int limit) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public int update(Owner entity) {

    }

    @Override
    public boolean delete(Integer id) {

    }

    @Override
    public List<Owner> findByNamePart(String namePart, int offset, int limit) {
        return null;
    }

    @Override
    public int findByNamePartCount(String namePart) {
        return 0;
    }
}
