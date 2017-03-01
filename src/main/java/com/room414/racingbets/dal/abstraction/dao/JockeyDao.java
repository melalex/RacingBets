package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.domain.entities.Jockey;

import java.util.List;

/**
 * DAO for Jockey entity.
 *
 * @see Jockey
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public interface JockeyDao extends PersonDao<Jockey> {
    /**
     * @return List of Jockeys whose name starts with namePart or empty list if no found.
     */
    List<Jockey> findByNamePart(String namePart, int offset, int limit);

}
