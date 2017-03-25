package com.room414.racingbets.web.controller;

import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.OwnerService;
import com.room414.racingbets.bll.abstraction.services.ParticipantService;
import com.room414.racingbets.bll.dto.entities.OwnerDto;
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
public class OwnerController {
    private static final String ENTITY_TYPE = "Owner";
    private ParticipantService participantService;
    private CrudControllerDelegate<OwnerDto, OwnerDto> crudControllerDelegate;

    private Locale locale;

    public OwnerController(
            OwnerService ownerService,
            AccountService accountService,
            ParticipantService participantService,
            Locale locale
    ) {
        this.participantService = participantService;
        this.locale = locale;

        this.crudControllerDelegate = new CrudControllerDelegate<>(
                ownerService,
                accountService,
                OwnerDto.class,
                OwnerDto.class,
                ENTITY_TYPE,
                locale,
                this::validate
        );
    }

    private void validate(OwnerDto form, ResponseBuilder<OwnerDto> responseBuilder) {
        validatePerson(form, responseBuilder, locale, ENTITY_TYPE);
    }

    /**
     * POST: /owner
     */
    public void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.create(req, resp);
    }

    /**
     * PUT: /owner
     */
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.update(req, resp);
    }

    /**
     * GET: /owner/{id}
     */
    public void findById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.findById(req, resp);
    }

    /**
     * GET: /owner?query={query};page={page}
     */
    public void find(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.find(req, resp);
    }

    /**
     * GET: /owner/participant/{id}?page={page}
     */
    public void findAsParticipant(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ControllerUtil.findAsParticipant(req, resp, locale, participantService::findByOwner);
    }

    /**
     * DELETE: owner/{id}
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.delete(req, resp);
    }
}
