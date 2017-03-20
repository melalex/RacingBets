package com.room414.racingbets.bll.abstraction.factories.services;

import com.room414.racingbets.bll.abstraction.services.RaceService;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public interface RaceServiceFactory {
    RaceService create();
}
