package com.room414.racingbets.web.controller;

import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.HorseService;
import com.room414.racingbets.bll.abstraction.services.ParticipantService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class HorseController {
    private HorseService horseService;
    private AccountService accountService;
    private ParticipantService participantService;

    public HorseController(HorseService horseService, AccountService accountService, ParticipantService participantService) {
        this.horseService = horseService;
        this.accountService = accountService;
        this.participantService = participantService;
    }

    /**
     * PUT: horse/
     */
    public void create(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * POST: horse/{id}
     */
    public void update(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: horse/{id}
     */
    public void find(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: horse/search/{namePart}
     */
    public void search(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: horse/all/{page}
     */
    public void findAll(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: horse/participant/{id}
     */
    public void findAsParticipant(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * DELETE: horse/{id}
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) {

    }
}
