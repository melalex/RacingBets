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
    public void register(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * POST: account/login
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * POST: account/add/role
     */
    public void addRole(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * POST: account/remove/role
     */
    public void removeRole(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * POST: account/add/money
     */
    public void addMoney(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * POST: account/confirm
     */
    public void confirmEmail(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: account/all/{page}
     */
    public void getAll(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: account/search/{loginPart}/{page}
     */
    public void search(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: account/detail/{id}
     */
    public void detail(HttpServletRequest req, HttpServletResponse resp) {

    }
}
