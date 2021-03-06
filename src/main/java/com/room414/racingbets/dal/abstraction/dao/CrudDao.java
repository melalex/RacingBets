package com.room414.racingbets.dal.abstraction.dao;


import java.util.List;

/**
 * Interface of DAO that provides default CRUD operation.
 *
 * @author Alexander Melashchenko
 * @version 1.0 26 Feb 2017
 */
public interface CrudDao<K, E> {
    /**
     * Add new entity to repository
     *
     * @param entity entity that should be added to data store.
     */
    void create(E entity);

    /**
     * Find entity by id.
     *
     * @param id id of entity
     * @return entity with id == param id or null if no found
     */
    E find(K id);

    /**
     * @return All entities that stores in data store.
     */
    List<E> findAll();


    /**
     * @param offset the number of items that need to skip
     * @param limit elements count in result
     * @return All entities that stores in data store.
     */
    List<E> findAll(int offset, int limit);

    /**
     * @return count of entities in data store.
     */
    int count();

    /**
     * Updates value of entity param in data store. If entity has inner list it will not be updated
     *
     * @param entity entity to update
     * @return count of updated entities
     */
    long update(E entity);

    /**
     * Removes entity T with specific id from data store.
     *
     * @param id id of entity to remove
     * @return is deleted
     */
    boolean delete(K id);
}
