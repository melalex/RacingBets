package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.domain.entities.FilterParams;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.RaceStatus;

import java.util.List;

/**
 * DAO for RaceDto entity
 *
 * @see Race
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public interface RaceDao extends CrudDao<Long, Race> {
    List<Race> filter(FilterParams params);
    int count(FilterParams params);

    boolean updateStatus(long id, RaceStatus status);
}
