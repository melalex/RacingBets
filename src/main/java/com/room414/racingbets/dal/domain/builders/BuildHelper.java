package com.room414.racingbets.dal.domain.builders;

import com.room414.racingbets.dal.domain.entities.Participant;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Util class that stores common methods for builders (prevents code duplication).
 *
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
class BuildHelper {
    static List<Participant> mapIdsToParticipants(List<Integer> ids) {
        if (ids != null) {
            return ids.stream().map((id) -> {
                Participant participant = new Participant();
                participant.setId(id);
                return participant;
            }).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
