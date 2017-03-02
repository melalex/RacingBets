package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.domain.entities.Country;

import java.util.List;

/**
 * DAO for Country entity
 *
 * @see Country
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public interface CountryDao extends CrudDao<Long, Country> {
    /**
     * @return List of Countries which names starts with namePart or empty list if no found.
     */
    List<Country> findByNamePart(String namePart, long offset, long limit);

    /**
     * @return count of Countries which names starts with namePart
     */
    int findByNamePartCount(String namePart);

}
