package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.infrastructure.Pair;

import java.sql.Timestamp;
import java.util.List;

/**
 * DAO for Participant entity
 *
 * @see Participant
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public interface ParticipantDao extends CrudDao<Integer, Participant> {
    /**
     * @return List of Participants and race timestamp.
     */
    List<Pair<Participant, Timestamp>> findByHorseId(int id, int offset, int limit);
    int findByHorseIdCount(int id);

    List<Pair<Participant, Timestamp>> findByOwnerId(int id, int offset, int limit);
    int findByOwnerIdCount(int id);

    List<Pair<Participant, Timestamp>> findByJockeyId(int id, int offset, int limit);
    int findByJockeyIdCount(int id);

    List<Pair<Participant, Timestamp>> findByTrainerId(int id, int offset, int limit);
    int findByTrainerIdCount(int id);

    // TODO: add some aggregation queries for statistic
}
