package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.dto.entities.ParticipantDto;
import com.room414.racingbets.bll.dto.entities.RaceParticipantThumbnailDto;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public interface ParticipantService {
    void update(ParticipantDto participant);
    void delete(long id);

    List<RaceParticipantThumbnailDto> findByHorse(long id, Pager pager);
    List<RaceParticipantThumbnailDto> findByOwner(long id, Pager pager);
    List<RaceParticipantThumbnailDto> findByJockey(long id, Pager pager);
    List<RaceParticipantThumbnailDto> findByTrainer(long id, Pager pager);
}
