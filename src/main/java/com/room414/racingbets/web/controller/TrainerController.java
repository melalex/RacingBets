package com.room414.racingbets.web.controller;

import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.ParticipantService;
import com.room414.racingbets.bll.abstraction.services.TrainerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class TrainerController {
    private TrainerService trainerService;
    private AccountService accountService;
    private ParticipantService participantService;

    public TrainerController(TrainerService trainerService, AccountService accountService, ParticipantService participantService) {
        this.trainerService = trainerService;
        this.accountService = accountService;
        this.participantService = participantService;
    }

    /**
     * PUT: trainer/
     */
    void create(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * POST: trainer/{id}
     */
    void update(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: trainer/{id}
     */
    void find(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: trainer/search/{namePart}
     */
    void search(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: trainer/all/{page}
     */
    void findAll(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: trainer/participant/{id}
     */
    void findAsParticipant(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * DELETE: trainer/{id}
     */
    void delete(HttpServletRequest req, HttpServletResponse resp) {

    }


}
