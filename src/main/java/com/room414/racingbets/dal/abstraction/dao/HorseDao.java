package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.entities.Horse;

import java.util.List;

/**
 * DAO for Horse entity
 *
 * @see Horse
 * @author Alexander Melashchenko
 * @version 1.0 26 Feb 2017
 */
public interface HorseDao extends CrudDao<Integer, Horse> {
    /**
     * @return List of Horses whose name starts with namePart or empty list if no found.
     */
    List<Horse> findByNamePart(String namePart, int offset, int limit);

    /**
     * @return count of Horses whose name starts with namePart
     */
    int findByNamePartCount(String namePart);
}
