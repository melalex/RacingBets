package com.room414.racingbets.web.controller.impl;

import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.HorseService;
import com.room414.racingbets.bll.dto.entities.HorseDto;
import com.room414.racingbets.web.controller.interfaces.CrudController;
import com.room414.racingbets.web.model.builders.ResponseBuilder;
import com.room414.racingbets.web.model.forms.HorseForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static com.room414.racingbets.web.util.ValidatorUtil.*;


/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class HorseController implements CrudController {
    private static final String ENTITY_TYPE = "Horse";

    private CrudControllerDelegate<HorseForm, HorseDto> crudControllerDelegate;

    private Locale locale;

    public HorseController(
            HorseService horseService,
            AccountService accountService,
            Locale locale
    ) {
        this.locale = locale;
        this.crudControllerDelegate = new CrudControllerDelegate<>(
                horseService,
                accountService,
                HorseForm.class,
                HorseDto.class,
                ENTITY_TYPE,
                locale,
                this::validate
        );
    }

    private void validate(HorseForm form, ResponseBuilder<HorseDto> responseBuilder) {
        notNull(form.getName(), responseBuilder, locale, "name", ENTITY_TYPE);
        notNull(form.getBirthday(), responseBuilder, locale, "birthday", ENTITY_TYPE);
        notNull(form.getGender(), responseBuilder, locale, "gender", ENTITY_TYPE);

        notNull(form.getOwner(), responseBuilder, locale, "owner", ENTITY_TYPE);
        notNull(form.getTrainer(), responseBuilder, locale, "trainer", ENTITY_TYPE);

        validateString(form.getName(), responseBuilder, locale, "name", ENTITY_TYPE);
        validateStringLength(form.getName(), STRING_MIN_LENGTH, STRING_MAX_LENGTH, responseBuilder, locale, "name", ENTITY_TYPE);

        validateRange(form.getOwner(), 1, Long.MAX_VALUE, responseBuilder, locale, "owner", ENTITY_TYPE);
        validateRange(form.getTrainer(), 1, Long.MAX_VALUE,responseBuilder, locale, "trainer", ENTITY_TYPE);
    }

    /**
     * POST: /horse
     */
    @Override
    public void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.create(req, resp);
    }

    /**
     * PUT: /horse
     */
    @Override
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.update(req, resp);
    }

    /**
     * GET: /horse/%d
     */
    @Override
    public void find(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.find(req, resp);
    }

    /**
     * GET: /horse?query=%s&page=%d
     */
    @Override
    public void search(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.search(req, resp);
    }

    /**
     * DELETE: horse/%d
     */
    @Override
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.delete(req, resp);
    }
}
