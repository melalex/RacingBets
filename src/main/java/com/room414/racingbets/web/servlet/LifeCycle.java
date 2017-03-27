package com.room414.racingbets.web.servlet;

import com.room414.racingbets.dal.concrete.facade.DalFacadeImpl;

/**
 * @author Alexander Melashchenko
 * @version 1.0 28 Mar 2017
 */
class LifeCycle {

    private LifeCycle() {

    }

    static void startUp(RacingBetsFrontController servlet) {

    }

    static void tearDown() {
        DalFacadeImpl.getInstance().close();
    }
}
