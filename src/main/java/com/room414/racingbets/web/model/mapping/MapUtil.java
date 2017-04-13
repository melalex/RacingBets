package com.room414.racingbets.web.model.mapping;

import com.room414.racingbets.bll.dto.entities.BetDto;
import com.room414.racingbets.bll.dto.entities.ParticipantDto;
import com.room414.racingbets.bll.dto.entities.UserDto;
import com.room414.racingbets.web.model.forms.BetForm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Apr 2017
 */
public class MapUtil {
    private MapUtil() {

    }

    public static BetDto betFormToDto(BetForm form) {
        BetDto dto = new BetDto();

        dto.setUser(new UserDto(form.getUser()));
        dto.setBetSize(form.getBetSize());
        dto.setBetType(form.getBetType());
        dto.setRaceId(form.getRaceId());

        Map<Integer, Long> participantsIds = form.getParticipants();
        Map<Integer, ParticipantDto> participants = new HashMap<>();

        for (Integer place : participantsIds.keySet()) {
            participants.put(place, new ParticipantDto(participantsIds.get(place)));
        }

        dto.setParticipants(participants);

        return dto;
    }
}
