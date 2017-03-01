package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.entities.Person;

import java.util.List;

/**
 * Base DAO interface for entities that inherits Person.
 *
 * @see Person
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public interface PersonDao<T extends Person> extends CrudDao<Integer, T> {
    /**
     * @return List of Persons whose name starts with namePart or empty list if no found.
     */
    List<? extends Person> findByNamePart(String namePart, int offset, int limit);

    /**
     * @return count of Persons whose name starts with namePart
     */
    int findByNamePartCount(String namePart);
}
