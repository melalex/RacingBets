package com.room414.racingbets.web.controller;

import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.OwnerService;
import com.room414.racingbets.bll.abstraction.services.ParticipantService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class OwnerController {
    private OwnerService ownerService;
    private AccountService accountService;
    private ParticipantService participantService;

    public OwnerController(OwnerService ownerService, AccountService accountService, ParticipantService participantService) {
        this.ownerService = ownerService;
        this.accountService = accountService;
        this.participantService = participantService;
    }

    /**
     * POST: owner/
     */
    public void create(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * PUT: owner/{id}
     */
    public void update(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: owner/{id}
     */
    public void find(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: owner/search/{namePart}
     */
    public void search(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: owner/all/{page}
     */
    public void findAll(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: owner/participant/{id}
     */
    public void findAsParticipant(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * DELETE: owner/{id}
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) {

    }

}
