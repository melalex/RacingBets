package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.domain.entities.Owner;

import java.util.List;

/**
 * DAO for Owner entity.
 *
 * @see Owner
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public interface OwnerDao extends PersonDao<Owner> {
    /**
     * @return List of Owners whose name starts with namePart or empty list if no found.
     */
    List<Owner> findByNamePart(String namePart, int offset, int limit);

}
