package com.room414.racingbets.bll.abstraction.factories.infrastructure;

import com.room414.racingbets.bll.abstraction.infrastructure.mail.EmailConfirmMessenger;
import com.room414.racingbets.bll.abstraction.infrastructure.mail.RaceResultMessenger;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public interface MessengerFactory {
    RaceResultMessenger createRaceResultMessenger();
    EmailConfirmMessenger createEmailConfirmMessenger();
}
