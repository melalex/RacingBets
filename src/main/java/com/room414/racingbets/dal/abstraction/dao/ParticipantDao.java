package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;

import java.sql.Timestamp;
import java.util.List;

/**
 * DAO for Participant entity
 *
 * @see Participant
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public interface ParticipantDao extends CrudDao<Long, Participant> {
    /**
     * @return List of Participants and race timestamp.
     */
    List<Pair<Participant, Timestamp>> findByHorseId(long id, long offset, long limit) throws DalException;
    long findByHorseIdCount(long id) throws DalException;

    List<Pair<Participant, Timestamp>> findByOwnerId(long id, long offset, long limit) throws DalException;
    long findByOwnerIdCount(long id) throws DalException;

    List<Pair<Participant, Timestamp>> findByJockeyId(long id, long offset, long limit) throws DalException;
    long findByJockeyIdCount(long id) throws DalException;

    List<Pair<Participant, Timestamp>> findByTrainerId(long id, long offset, long limit) throws DalException;
    long findByTrainerIdCount(long id) throws DalException;

    // TODO: add some aggregation queries for statistic
}
