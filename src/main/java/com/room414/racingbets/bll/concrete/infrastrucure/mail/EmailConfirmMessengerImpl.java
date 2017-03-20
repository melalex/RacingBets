package com.room414.racingbets.bll.concrete.infrastrucure.mail;

import com.room414.racingbets.bll.abstraction.infrastructure.mail.EmailConfirmMessenger;
import com.room414.racingbets.bll.dto.entities.UserDto;

import java.util.Locale;
import java.util.Properties;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public class EmailConfirmMessengerImpl implements EmailConfirmMessenger {
    private Properties properties;

    public EmailConfirmMessengerImpl(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void sendConfirmMessage(UserDto user, String token, Locale locale) {

    }
}
