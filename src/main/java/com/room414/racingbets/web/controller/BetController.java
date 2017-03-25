package com.room414.racingbets.web.controller;

import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.BetService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class BetController {
    private BetService betService;
    private AccountService accountService;

    public BetController(BetService betService, AccountService accountService) {
        this.betService = betService;
        this.accountService = accountService;
    }

    /**
     * POST: bet/
     */
    public void makeBet(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: bet/odds
     */
    public void getOdds(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: bet/user/{id}
     */
    public void getBetsByUser(HttpServletRequest req, HttpServletResponse resp) {

    }
}
