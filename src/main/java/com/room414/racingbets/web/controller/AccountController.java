package com.room414.racingbets.web.controller;

import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.MessageService;
import com.room414.racingbets.bll.abstraction.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class AccountController {
    private AccountService accountService;
    private UserService userService;
    private MessageService messageService;

    public AccountController(AccountService accountService, UserService userService, MessageService messageService) {
        this.accountService = accountService;
        this.userService = userService;
        this.messageService = messageService;
    }

    /**
     * PUT: account/register
     */
    void register(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * POST: account/login
     */
    void login(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * POST: account/add/role
     */
    void addRole(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * POST: account/remove/role
     */
    void removeRole(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * POST: account/add/money
     */
    void addMoney(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * POST: account/confirm
     */
    void confirmEmail(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: account/all/{page}
     */
    void getAll(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: account/search/{loginPart}/{page}
     */
    void search(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: account/detail/{id}
     */
    void detail(HttpServletRequest req, HttpServletResponse resp) {

    }
}
