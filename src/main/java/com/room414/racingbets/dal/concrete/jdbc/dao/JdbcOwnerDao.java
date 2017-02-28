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

    /**
     * Add new entity to repository
     *
     * @param entity entity that should be added to data store.
     */
    @Override
    public void create(Owner entity) {

    }

    /**
     * Find entity by id.
     *
     * @param id id of entity
     * @return entity with id == param id
     */
    @Override
    public Owner find(Integer id) {
        return null;
    }

    /**
     * @return All entities T that stores in data store.
     */
    @Override
    public List<Owner> findAll() {
        return null;
    }

    /**
     * Updates value of entity param in data store
     *
     * @param entity entity to update
     */
    @Override
    public void update(Owner entity) {

    }

    /**
     * Removes entity T with specific id from data store.
     *
     * @param id id of entity to remove
     */
    @Override
    public void delete(Integer id) {

    }
}
