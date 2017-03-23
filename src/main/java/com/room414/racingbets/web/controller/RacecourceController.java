package com.room414.racingbets.web.controller;

import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.ParticipantService;
import com.room414.racingbets.bll.abstraction.services.RacecourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class RacecourceController {
    private RacecourseService racecourseService;
    private AccountService accountService;
    private ParticipantService participantService;

    public RacecourceController(RacecourseService racecourseService, AccountService accountService, ParticipantService participantService) {
        this.racecourseService = racecourseService;
        this.accountService = accountService;
        this.participantService = participantService;
    }

    /**
     * PUT: racecourse/
     */
    void create(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * POST: racecourse/{id}
     */
    void update(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: racecourse/{id}
     */
    void find(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: racecourse/search/{namePart}
     */
    void search(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: racecourse/all/{page}
     */
    void findAll(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: racecourse/participant/{id}
     */
    void findAsParticipant(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * DELETE: racecourse/{id}
     */
    void delete(HttpServletRequest req, HttpServletResponse resp) {

    }

}
