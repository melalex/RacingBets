package com.room414.racingbets.web.controller;

import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.JockeyService;
import com.room414.racingbets.bll.abstraction.services.ParticipantService;
import com.room414.racingbets.bll.dto.entities.JockeyDto;
import com.room414.racingbets.web.model.builders.ResponseBuilder;
import com.room414.racingbets.web.util.ControllerUtil;

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
    private ParticipantService participantService;
    private CrudControllerDelegate<JockeyDto, JockeyDto> crudControllerDelegate;

    private Locale locale;

    public JockeyController(
            JockeyService jockeyService,
            AccountService accountService,
            ParticipantService participantService,
            Locale locale
    ) {
        this.participantService = participantService;
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
     * GET: /jockey/{id}
     */
    public void findById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.findById(req, resp);
    }

    /**
     * GET: /jockey?query={query};page={page}
     */
    public void find(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.find(req, resp);
    }

    /**
     * GET: /jockey/participant/{id}?page={page}
     */
    public void findAsParticipant(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ControllerUtil.findAsParticipant(req, resp, locale, participantService::findByJockey);
    }

    /**
     * DELETE: jockey/{id}
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.delete(req, resp);
    }
}
