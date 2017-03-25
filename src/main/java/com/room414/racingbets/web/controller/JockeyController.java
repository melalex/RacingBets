package com.room414.racingbets.web.controller;

import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.JockeyService;
import com.room414.racingbets.bll.abstraction.services.ParticipantService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class JockeyController {
    private JockeyService jockeyService;
    private AccountService accountService;
    private ParticipantService participantService;

    public JockeyController(JockeyService jockeyService, AccountService accountService, ParticipantService participantService) {
        this.jockeyService = jockeyService;
        this.accountService = accountService;
        this.participantService = participantService;
    }

    /**
     * POST: jockey/
     */
    public void create(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * PUT: jockey/{id}
     */
    public void update(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: jockey/{id}
     */
    public void find(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: jockey/search/{namePart}
     */
    public void search(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: jockey/all/{page}
     */
    public void findAll(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: jockey/participant/{id}
     */
    public void findAsParticipant(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * DELETE: jockey/{id}
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) {

    }
}
