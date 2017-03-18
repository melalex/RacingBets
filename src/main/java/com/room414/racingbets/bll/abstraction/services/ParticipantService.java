package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.dto.entities.ParticipantDto;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public interface ParticipantService {
    void updatePaticipant(ParticipantDto race);
    void deletePaticipant(ParticipantDto race);

    List<ParticipantDto> findByHorse(long id, Pager pager);
    List<ParticipantDto> findByOwner(long id, Pager pager);
    List<ParticipantDto> findByJockey(long id, Pager pager);
    List<ParticipantDto> findByTrainer(long id, Pager pager);
}
