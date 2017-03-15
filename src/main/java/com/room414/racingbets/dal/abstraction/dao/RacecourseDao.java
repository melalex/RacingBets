package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Racecourse;

import java.util.List;

/**
 * DAO for Racecourse entity
 *
 * @see Racecourse
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public interface RacecourseDao extends CrudDao<Long, Racecourse> {
    /**
     * @return List of Racecourses which names starts with namePart or empty list if no found.
     */
    List<Racecourse> findByNamePart(String namePart, long offset, long limit);

    /**
     * @return count of Racecourses which names starts with namePart
     */
    long findByNamePartCount(String namePart);
}
