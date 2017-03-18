package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.dto.entities.RaceDto;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public interface RaceService {
    void scheduleRace(RaceDto race);
    void rejecteRace(RaceDto race);
    void finishRace(RaceDto race);

    List<RaceDto> findScheduledByRacecourse(long id, Pager pager);
    List<RaceDto> findFinishedByRacecourse(long id, Pager pager);

    List<RaceDto> findScheduledByDate(Date date, Pager pager);
    List<RaceDto> findFinishedByDate(Date date, Pager pager);

    List<RaceDto> findScheduledByDateAndRacecourse(Date date, long id, Pager pager);
    List<RaceDto> findFinishedByDateAndRacecourse(Date date, long id, Pager pager);
}
