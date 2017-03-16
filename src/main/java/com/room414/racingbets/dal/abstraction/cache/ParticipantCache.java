package com.room414.racingbets.dal.abstraction.cache;

import com.room414.racingbets.dal.abstraction.infrastructure.Getter;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;
import com.room414.racingbets.dal.domain.entities.Participant;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 16 Mar 2017
 */
public interface ParticipantCache extends EntityCache<Participant> {
    List<Pair<Participant, Timestamp>> getWhoAndWhenCached(String key, Getter<List<Pair<Participant, Timestamp>>> getter);
    void deleteWhoAndWhen();
}
