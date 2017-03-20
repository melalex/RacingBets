package com.room414.racingbets.bll.abstraction.infrastructure.mail;

import com.room414.racingbets.bll.dto.entities.UserDto;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public interface RaceResultMessenger {
    void sendWinMessage(UserDto user, Locale locale);
    void sendLoseMessage(UserDto user, Locale locale);
    void sendRejectMessage(UserDto user, Locale locale);
}