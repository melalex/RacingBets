package com.room414.racingbets.dal.abstraction.cache;

import com.room414.racingbets.dal.abstraction.infrastructure.Getter;
import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.domain.entities.RaceParticipantThumbnail;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 16 Mar 2017
 */
public interface ParticipantCache extends EntityCache<Participant> {
    List<RaceParticipantThumbnail> getThumbnailCached(String key, Getter<List<RaceParticipantThumbnail>> getter);
    void deleteThumbnail();
}
