package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.services.MessageService;
import com.room414.racingbets.bll.dto.entities.BetDto;
import com.room414.racingbets.bll.dto.entities.RaceDto;
import com.room414.racingbets.bll.dto.entities.UserDto;

import java.util.Locale;
import java.util.Properties;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class MessageServiceImpl implements MessageService {
    private Properties properties;

    public MessageServiceImpl(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void sendWinMessage(UserDto user, BetDto bet, RaceDto race, Locale locale) {

    }

    @Override
    public void sendLoseMessage(UserDto user, BetDto bet, RaceDto race, Locale locale) {

    }

    @Override
    public void sendRejectMessage(UserDto user, BetDto bet, RaceDto race, Locale locale) {

    }

    @Override
    public void sendConfirmMessage(UserDto user, String token, Locale locale) {

    }
}
