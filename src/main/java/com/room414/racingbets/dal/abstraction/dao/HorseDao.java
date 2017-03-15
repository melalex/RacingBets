package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.abstraction.exception.DalException;

import java.util.List;

/**
 * DAO for Horse entity
 *
 * @see Horse
 * @author Alexander Melashchenko
 * @version 1.0 26 Feb 2017
 */
public interface HorseDao extends CrudDao<Long, Horse> {
    /**
     * @return List of Horses whose name starts with namePart or empty list if no found.
     */
    List<Horse> findByNamePart(String namePart, long offset, long limit);

    /**
     * @return count of Horses whose name starts with namePart
     */
    long findByNamePartCount(String namePart);
}
