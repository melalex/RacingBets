package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.dto.entities.ParticipantDto;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public interface ParticipantService {
    void update(ParticipantDto participant);
    void delete(long id);

    List<Pair<ParticipantDto, Date>> findByHorse(long id, Pager pager);
    List<Pair<ParticipantDto, Date>> findByOwner(long id, Pager pager);
    List<Pair<ParticipantDto, Date>> findByJockey(long id, Pager pager);
    List<Pair<ParticipantDto, Date>> findByTrainer(long id, Pager pager);
}
