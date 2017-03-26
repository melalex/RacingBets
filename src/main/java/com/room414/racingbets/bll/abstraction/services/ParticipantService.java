package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.dto.entities.ParticipantDto;

/**
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public interface ParticipantService {
    void update(ParticipantDto participant);
    void delete(long id);
}
