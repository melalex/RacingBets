package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.abstraction.builders.FilterParamsBuilder;
import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.dto.entities.RaceDto;
import com.room414.racingbets.dal.domain.entities.Race;
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
    void rejectRace(RaceDto race);
    void finishRace(RaceDto race);

    void update(RaceDto race);
    void delete(long id);

    RaceDto find(long id);

    FilterParamsBuilder filterParamsBuilder();

    List<Race> filter(FilterParamsBuilder builder);
}
