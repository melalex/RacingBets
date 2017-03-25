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
     * POST: racecourse/
     */
    public void create(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * PUT: racecourse/{id}
     */
    public void update(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: racecourse/{id}
     */
    public void find(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: racecourse/search/{namePart}
     */
    public void search(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: racecourse/all/{page}
     */
    public void findAll(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: racecourse/participant/{id}
     */
    public void findAsParticipant(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * DELETE: racecourse/{id}
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) {

    }

}
