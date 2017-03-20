package com.room414.racingbets.bll.concrete.factories.infrastructure;

import com.room414.racingbets.bll.abstraction.factories.infrastructure.MessengerFactory;
import com.room414.racingbets.bll.abstraction.infrastructure.mail.EmailConfirmMessenger;
import com.room414.racingbets.bll.abstraction.infrastructure.mail.RaceResultMessenger;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public class MessengerFactoryImpl implements MessengerFactory {
    @Override
    public RaceResultMessenger createRaceResultMessenger() {
        return null;
    }

    @Override
    public EmailConfirmMessenger createEmailConfirmMessenger() {
        return null;
    }
}
