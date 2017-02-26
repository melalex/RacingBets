package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.entities.Person;

/**
 * Base DAO interface for entities that inherits Person.
 *
 * @see Person
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public interface PersonDao<T extends Person> extends CrudDao<Integer, T> {
}
