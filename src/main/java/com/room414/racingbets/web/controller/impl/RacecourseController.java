package com.room414.racingbets.web.controller.impl;

import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.RacecourseService;
import com.room414.racingbets.bll.dto.entities.RacecourseDto;
import com.room414.racingbets.web.controller.interfaces.CrudController;
import com.room414.racingbets.web.model.builders.ResponseBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static com.room414.racingbets.web.util.ValidatorUtil.*;
import static com.room414.racingbets.web.util.ValidatorUtil.STRING_MAX_LENGTH;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class RacecourseController implements CrudController {
    private static final String ENTITY_TYPE = "Racecourse";
    private CrudControllerDelegate<RacecourseDto, RacecourseDto> crudControllerDelegate;

    private Locale locale;

    public RacecourseController(
            RacecourseService racecourseService,
            AccountService accountService,
            Locale locale
    ) {
        this.locale = locale;

        this.crudControllerDelegate = new CrudControllerDelegate<>(
                racecourseService,
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

        validateStringLength(form.getName(), STRING_MIN_LENGTH, STRING_MAX_LENGTH, responseBuilder, locale, "name", ENTITY_TYPE);
        validateStringLength(form.getClerk(), STRING_MIN_LENGTH, STRING_MAX_LENGTH, responseBuilder, locale, "clerk", ENTITY_TYPE);
        validateStringLength(form.getContact(), STRING_MIN_LENGTH, STRING_MAX_LENGTH, responseBuilder, locale, "contact", ENTITY_TYPE);

    }

    /**
     * POST: /racecourse
     */
    @Override
    public void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.create(req, resp);
    }

    /**
     * PUT: /racecourse
     */
    @Override
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.update(req, resp);
    }

    /**
     * GET: /racecourse/%d
     */
    @Override
    public void find(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.find(req, resp);
    }

    /**
     * GET: /racecourse?query=%s&page=%d
     */
    @Override
    public void search(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.search(req, resp);
    }

    /**
     * DELETE: racecourse/%d
     */
    @Override
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.delete(req, resp);
    }
}
