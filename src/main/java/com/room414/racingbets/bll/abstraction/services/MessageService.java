package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.dto.entities.BetDto;
import com.room414.racingbets.bll.dto.entities.RaceDto;
import com.room414.racingbets.bll.dto.entities.UserDto;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public interface MessageService {
    void sendWinMessage(BetDto bet, RaceDto race);
    void sendLoseMessage(BetDto bet, RaceDto race);
    void sendRejectMessage(BetDto bet, RaceDto race);

    void sendConfirmMessage(UserDto user, String token);
}
