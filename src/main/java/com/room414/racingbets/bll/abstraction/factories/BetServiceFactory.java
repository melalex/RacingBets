package com.room414.racingbets.bll.abstraction.factories;

import com.room414.racingbets.bll.abstraction.services.BetService;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public interface BetServiceFactory {
    BetService create();
}
