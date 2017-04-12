package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.abstraction.infrastructure.BetError;
import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.dto.entities.BetDto;
import com.room414.racingbets.bll.dto.entities.OddsDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public interface BetService {
    BigDecimal makeBet(BetDto bet, Consumer<BetError> onError);
    OddsDto getOdds(BetDto bet, Consumer<BetError> onError);
    List<BetDto> getBetsByUser(long id, Pager pager);
}
