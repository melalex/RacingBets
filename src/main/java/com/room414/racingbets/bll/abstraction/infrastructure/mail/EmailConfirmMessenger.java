package com.room414.racingbets.bll.abstraction.infrastructure.mail;

import com.room414.racingbets.bll.dto.entities.UserDto;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public interface EmailConfirmMessenger {
    void sendConfirmMessage(UserDto user, String token, Locale locale);
}
