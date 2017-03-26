package com.room414.racingbets.bll.abstraction.infrastructure;

import com.room414.racingbets.dal.domain.entities.FilterParams;
import com.room414.racingbets.dal.domain.enums.RaceStatus;

import java.util.Date;

/**
 * @author Alexander Melashchenko
 * @version 1.0 26 Mar 2017
 */
public interface FilterParamsBuilder {
    FilterParamsBuilder setRaceStatus(RaceStatus raceStatus);
    FilterParamsBuilder setId(Long id);
    FilterParamsBuilder setRacecourseId(Long racecourseId);
    FilterParamsBuilder setHorseId(Long horseId);
    FilterParamsBuilder setTrainerId(Long trainerId);
    FilterParamsBuilder setJockeyId(Long jockeyId);
    FilterParamsBuilder setName(String name);
    FilterParamsBuilder setDate(Date date);
    FilterParamsBuilder setLimit(int limit);
    FilterParamsBuilder setOffset(int offset);

    FilterParams build();
}
