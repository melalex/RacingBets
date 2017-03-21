package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.dto.entities.RaceDto;
import com.room414.racingbets.dal.domain.enums.RaceStatus;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public interface RaceService {
    void scheduleRace(RaceDto race);
    void startRace(long id);
    void rejectRace(long id);
    void finishRace(RaceDto race);

    void update(RaceDto horse);
    void delete(long id);

    RaceDto find(long id);
    List<RaceDto> findAll(Pager pager);

    List<RaceDto> findByRacecourse(RaceStatus status, long id, Pager pager);
    List<RaceDto> findByDate(RaceStatus status, Date date, Pager pager);
    List<RaceDto> findByDateAndRacecourse(RaceStatus status, Date date, long id, Pager pager);
    List<RaceDto> findByName(RaceStatus status, String name, Pager pager);
}
