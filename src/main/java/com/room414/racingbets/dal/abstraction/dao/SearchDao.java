package com.room414.racingbets.dal.abstraction.dao;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 19 Mar 2017
 */
public interface SearchDao<K, E> extends CrudDao<K, E> {
    /**
     * Search {@code E} by prefix.
     *
     * @param searchString begin of searching field
     * @param offset the number of items that need to skip
     * @param limit elements count in result
     * @return List of {@code E} whose searching field starts with {@code searchString} or empty if no found.
     */
    List<E> search(String searchString, int offset, int limit);

    /**
     * @param searchString begin of UserDto login
     * @return count ApplicationUsers whose login starts with loginPart
     */
    int searchCount(String searchString);
}
