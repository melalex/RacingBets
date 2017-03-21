package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.dto.entities.BetDto;
import com.room414.racingbets.bll.dto.entities.RaceDto;
import com.room414.racingbets.bll.dto.entities.UserDto;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public interface MessageService {
    void sendWinMessage(UserDto user, BetDto bet, RaceDto race, Locale locale);
    void sendLoseMessage(UserDto user, BetDto bet, RaceDto race, Locale locale);
    void sendRejectMessage(UserDto user, BetDto bet, RaceDto race, Locale locale);

    void sendConfirmMessage(UserDto user, String token, Locale locale);
}
