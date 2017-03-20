package com.room414.racingbets.bll.concrete.infrastrucure;

import com.room414.racingbets.bll.abstraction.infrastructure.Messenger;
import com.room414.racingbets.bll.dto.entities.UserDto;

import java.util.Locale;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class MessengerImpl implements Messenger {
    @Override
    public void sendWinMessage(UserDto user, Locale locale) {

    }

    @Override
    public void sendLoseMessage(UserDto user, Locale locale) {

    }

    @Override
    public void sendRejectMessage(UserDto user, Locale locale) {

    }

    @Override
    public void sendConfirmMessage(UserDto user, String token, Locale locale) {

    }
}
