package com.room414.racingbets.dal.abstraction.dao;


import com.room414.racingbets.dal.domain.entities.Horse;

import java.util.List;

/**
 * DAO for Horse entity
 *
 * @see Horse
 * @author Alexander Melashchenko
 * @version 1.0 26 Feb 2017
 */
public interface HorseDao extends SearchDao<Long, Horse> {

}
