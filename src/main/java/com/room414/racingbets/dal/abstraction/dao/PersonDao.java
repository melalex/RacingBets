package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.domain.base.Person;

import java.util.List;

/**
 * base DAO interface for entities that inherits Person.
 *
 * @see Person
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public interface PersonDao<T extends Person> extends CrudDao<Long, T> {
    /**
     * @return List of Persons whose name starts with namePart or empty list if no found.
     */
    List<T> findByNamePart(String namePart, long offset, long limit);

    /**
     * @return count of Persons whose name starts with namePart
     */
    long findByNamePartCount(String namePart);
}
