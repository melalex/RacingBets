package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.BetDao;
import com.room414.racingbets.dal.domain.entities.Bet;

import java.util.List;

/**
 * Implementation of BetDao that uses JDBC as data source.
 *
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcBetDao implements BetDao {
    /**
     * Add new entity to repository
     *
     * @param entity entity that should be added to data store.
     */
    @Override
    public void create(Bet entity) {

    }

    /**
     * Find entity by id.
     *
     * @param id id of entity
     * @return entity with id == param id
     */
    @Override
    public Bet find(Integer id) {
        return null;
    }

    /**
     * @return All entities T that stores in data store.
     */
    @Override
    public List<Bet> findAll() {
        return null;
    }

    /**
     * Updates value of entity param in data store
     *
     * @param entity entity to update
     */
    @Override
    public void update(Bet entity) {

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
