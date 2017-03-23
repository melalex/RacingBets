package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;
import com.room414.racingbets.dal.domain.entities.RaceParticipantThumbnail;

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
    List<RaceParticipantThumbnail> findByHorseId(long id, int offset, int limit);
    int findByHorseIdCount(long id);

    List<RaceParticipantThumbnail> findByOwnerId(long id, int offset, int limit);
    int findByOwnerIdCount(long id);

    List<RaceParticipantThumbnail> findByJockeyId(long id, int offset, int limit);
    int findByJockeyIdCount(long id);

    List<RaceParticipantThumbnail> findByTrainerId(long id, int offset, int limit);
    int findByTrainerIdCount(long id);

    // TODO: add some aggregation queries for statistic
}
