package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;

import java.sql.Timestamp;
import java.util.List;

/**
 * DAO for ParticipantDto entity
 *
 * @see Participant
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public interface ParticipantDao extends CrudDao<Long, Participant> {
    /**
     * @return List of Participants and race timestamp.
     */
    List<Pair<Participant, Timestamp>> findByHorseId(long id, long offset, long limit);
    long findByHorseIdCount(long id);

    List<Pair<Participant, Timestamp>> findByOwnerId(long id, long offset, long limit);
    long findByOwnerIdCount(long id);

    List<Pair<Participant, Timestamp>> findByJockeyId(long id, long offset, long limit);
    long findByJockeyIdCount(long id);

    List<Pair<Participant, Timestamp>> findByTrainerId(long id, long offset, long limit);
    long findByTrainerIdCount(long id);

    // TODO: add some aggregation queries for statistic
}
