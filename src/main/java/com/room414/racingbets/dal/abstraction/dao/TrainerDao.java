package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.domain.entities.Trainer;

import java.util.List;

/**
 * DAO for Trainer entity.
 *
 * @see Trainer
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public interface TrainerDao extends PersonDao<Trainer> {
    /**
     * @return List of Trainers whose name starts with namePart or empty list if no found.
     */
    List<Trainer> findByNamePart(String namePart, int offset, int limit);

}
