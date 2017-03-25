package com.room414.racingbets.web.controller;

import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.RaceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class RaceController {
    private RaceService raceService;
    private AccountService accountService;

    public RaceController(RaceService raceService, AccountService accountService) {
        this.raceService = raceService;
        this.accountService = accountService;
    }

    /**
     * POST: race/
     */
    public void scheduleRace(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * PUT: race/start/{id}
     */
    public void startRace(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * PUT: race/reject/{id}
     */
    public void rejectRace(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * PUT: race/finish/{id}
     */
    public void finishRace(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * PUT: race/update/{id}
     */
    public void update(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * DELETE: race/{id}
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: race/{id}
     */
    public void find(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: race/{page}
     */
    public void findAll(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: race/racecourse/{id}/{page}
     */
    public void findByRacecourse(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: race/date/{day}/{page}
     */
    public void findByDate(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: race/{id}/{day}/{page}
     */
    public void findByDateAndRacecourse(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: race/{name}/{page}
     */
    public void findByName(HttpServletRequest req, HttpServletResponse resp) {

    }
}
