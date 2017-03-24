package com.room414.racingbets.web.controller;

import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.HorseService;
import com.room414.racingbets.bll.abstraction.services.ParticipantService;
import com.room414.racingbets.bll.dto.entities.HorseDto;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.web.util.ControllerUtil;

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

    private boolean isValid(HorseDto dto) {
        return false;
    }

    /**
     * PUT: horse/
     */
    public void create(HttpServletRequest req, HttpServletResponse resp) {
        String token = ControllerUtil.getTokenFromRequest(req);
        if (accountService.isInRole(token, Role.ADMIN)) {
            
        } else {
            // TODO: add logic
        }
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
