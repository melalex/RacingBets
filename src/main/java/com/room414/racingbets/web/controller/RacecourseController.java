package com.room414.racingbets.web.controller;

import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.RacecourseService;
import com.room414.racingbets.bll.dto.entities.RacecourseDto;
import com.room414.racingbets.web.model.builders.ResponseBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static com.room414.racingbets.web.util.ValidatorUtil.notNull;
import static com.room414.racingbets.web.util.ValidatorUtil.validateString;
import static com.room414.racingbets.web.util.ValidatorUtil.validateStringLength;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class RacecourseController {
    private static final String ENTITY_TYPE = "Racecourse";
    private CrudControllerDelegate<RacecourseDto, RacecourseDto> crudControllerDelegate;

    private Locale locale;

    RacecourseController(
            RacecourseService ownerService,
            AccountService accountService,
            Locale locale
    ) {
        this.locale = locale;

        this.crudControllerDelegate = new CrudControllerDelegate<>(
                ownerService,
                accountService,
                RacecourseDto.class,
                RacecourseDto.class,
                ENTITY_TYPE,
                locale,
                this::validate
        );
    }

    private void validate(RacecourseDto form, ResponseBuilder<RacecourseDto> responseBuilder) {
        notNull(form.getName(), responseBuilder, locale, "name", ENTITY_TYPE);
        notNull(form.getClerk(), responseBuilder, locale, "clerk", ENTITY_TYPE);
        notNull(form.getContact(), responseBuilder, locale, "contact", ENTITY_TYPE);

        validateString(form.getName(), responseBuilder, locale, "name", ENTITY_TYPE);
        validateString(form.getClerk(), responseBuilder, locale, "clerk", ENTITY_TYPE);
        validateString(form.getContact(), responseBuilder, locale, "contact", ENTITY_TYPE);

        validateStringLength(form.getName(), 4, 45, responseBuilder, locale, "name", ENTITY_TYPE);
        validateStringLength(form.getClerk(), 4, 45, responseBuilder, locale, "clerk", ENTITY_TYPE);
        validateStringLength(form.getContact(), 4, 45, responseBuilder, locale, "contact", ENTITY_TYPE);

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
     * GET: /owner/%d
     */
    public void findById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.find(req, resp);
    }

    /**
     * GET: /owner?query=%s;page=%d
     */
    public void find(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.search(req, resp);
    }

    /**
     * DELETE: owner/%d
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.delete(req, resp);
    }
}
