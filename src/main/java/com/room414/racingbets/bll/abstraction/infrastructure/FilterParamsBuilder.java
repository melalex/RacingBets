package com.room414.racingbets.bll.abstraction.infrastructure;

import com.room414.racingbets.dal.domain.entities.FilterParams;

/**
 * @author Alexander Melashchenko
 * @version 1.0 26 Mar 2017
 */
public interface FilterParamsBuilder {
    FilterParamsBuilder setRaceStatus(String value);
    FilterParamsBuilder setRacecourseId(String value);
    FilterParamsBuilder setHorseId(String value);
    FilterParamsBuilder setTrainerId(String value);
    FilterParamsBuilder setJockeyId(String value);
    FilterParamsBuilder setName(String value);
    FilterParamsBuilder setDate(String value);
    FilterParamsBuilder setLimit(int limit);
    FilterParamsBuilder setOffset(int offset);

    FilterParams build();
}
