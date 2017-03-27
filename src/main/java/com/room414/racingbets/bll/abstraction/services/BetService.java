package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.dto.entities.BetDto;
import com.room414.racingbets.bll.dto.entities.OddsDto;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public interface BetService {
    void makeBet(BetDto bet);
    OddsDto getOdds(BetDto bet);
    List<BetDto> getBetsByUser(long id, Pager pager);
}
