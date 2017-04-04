package com.room414.racingbets.web.init;

import com.room414.racingbets.dal.concrete.facade.DalFacade;

import static com.room414.racingbets.web.init.AppInitializer.initApp;

/**
 * @author Alexander Melashchenko
 * @version 1.0 04 Apr 2017
 */
public class DatabaseInitializer {
    private static void initUsers() {

    }

    private static void initHorses() {

    }

    private static void initJockeys() {

    }

    private static void initOwners() {

    }

    private static void initRacecourses() {

    }

    private static void initRaces() {

    }

    private static void initTrainers() {

    }

    public static void main(String[] args) {
        initApp();

        initUsers();
        initHorses();
        initJockeys();
        initJockeys();
        initOwners();
        initTrainers();
        initRacecourses();
        initRaces();

        DalFacade.getInstance().close();
    }
}
