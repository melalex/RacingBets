package com.room414.racingbets.web.controller.impl;

import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.TrainerService;
import com.room414.racingbets.bll.dto.entities.TrainerDto;
import com.room414.racingbets.web.controller.interfaces.CrudController;
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
public class TrainerController implements CrudController {
    private static final String ENTITY_TYPE = "Trainer";
    private CrudControllerDelegate<TrainerDto, TrainerDto> crudControllerDelegate;

    private Locale locale;

    public TrainerController(
            TrainerService trainerService,
            AccountService accountService,
            Locale locale
    ) {
        this.locale = locale;
        this.crudControllerDelegate = new CrudControllerDelegate<>(
                trainerService,
                accountService,
                TrainerDto.class,
                TrainerDto.class,
                ENTITY_TYPE,
                locale,
                this::validate
        );
    }

    private void validate(TrainerDto form, ResponseBuilder<TrainerDto> responseBuilder) {
        validatePerson(form, responseBuilder, locale, ENTITY_TYPE);
    }

    /**
     * POST: /trainer
     */
    @Override
    public void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.create(req, resp);
    }

    /**
     * PUT: /trainer
     */
    @Override
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.update(req, resp);
    }

    /**
     * GET: /trainer/%d
     */
    @Override
    public void find(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.find(req, resp);
    }

    /**
     * GET: /trainer?query=%s&page=%d
     */
    @Override
    public void search(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.search(req, resp);
    }

    /**
     * DELETE: trainer/%d
     */
    @Override
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        crudControllerDelegate.delete(req, resp);
    }
}
