package com.room414.racingbets.web.controller;

import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.JockeyService;
import com.room414.racingbets.bll.dto.entities.JockeyDto;
import com.room414.racingbets.web.model.builders.ResponseBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static com.room414.racingbets.web.util.ValidatorUtil.validatePerson;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class JockeyController {
    private static final String ENTITY_TYPE = "Jockey";
    private CrudControllerDelegate<JockeyDto, JockeyDto> crudControllerDelegate;

    private Locale locale;

    JockeyController(
            JockeyService jockeyService,
            AccountService accountService,
            Locale locale
    ) {
        this.locale = locale;
        this.crudControllerDelegate = new CrudControllerDelegate<>(
                jockeyService,
                accountService,
                JockeyDto.class,
                JockeyDto.class,
                ENTITY_TYPE,
                locale,
                this::validate
        );
    }

    private void validate(JockeyDto form, ResponseBuilder<JockeyDto> responseBuilder) {
        validatePerson(form, responseBuilder, locale, ENTITY_TYPE);
    }

    /**
     * POST: /jockey
     */
    public void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.create(req, resp);
    }

    /**
     * PUT: /jockey
     */
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.update(req, resp);
    }

    /**
     * GET: /jockey/%d
     */
    public void find(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.find(req, resp);
    }

    /**
     * GET: /jockey?query=%s;page=%d
     */
    public void search(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.search(req, resp);
    }

    /**
     * DELETE: jockey/%d
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.delete(req, resp);
    }
}
