package com.room414.racingbets.web.infrastructure;

import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.dto.entities.RaceParticipantThumbnailDto;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 25 Mar 2017
 */
@FunctionalInterface
public interface ParticipantGetter {
    List<RaceParticipantThumbnailDto> call(long id, Pager pager);
}
