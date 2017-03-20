package com.room414.racingbets.bll.concrete.infrastrucure.mail;

import com.room414.racingbets.bll.abstraction.infrastructure.mail.RaceResultMessenger;
import com.room414.racingbets.bll.dto.entities.UserDto;

import java.util.Locale;
import java.util.Properties;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class RaceResultMessengerImpl implements RaceResultMessenger {
    private Properties properties;

    public RaceResultMessengerImpl(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void sendWinMessage(UserDto user, Locale locale) {

    }

    @Override
    public void sendLoseMessage(UserDto user, Locale locale) {

    }

    @Override
    public void sendRejectMessage(UserDto user, Locale locale) {

    }
}
