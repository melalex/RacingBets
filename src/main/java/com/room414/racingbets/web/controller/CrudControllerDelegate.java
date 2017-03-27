package com.room414.racingbets.web.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.CrudService;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.web.infrastructure.PagerImpl;
import com.room414.racingbets.web.infrastructure.Validator;
import com.room414.racingbets.web.model.builders.ResponseBuilder;
import com.room414.racingbets.web.util.ControllerUtil;
import com.room414.racingbets.web.util.ResponseUtil;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.room414.racingbets.web.util.ControllerUtil.map;
import static com.room414.racingbets.web.util.RequestUtil.getObject;
import static com.room414.racingbets.web.util.RequestUtil.getPageFromRequest;
import static com.room414.racingbets.web.util.RequestUtil.getJwtToken;
import static com.room414.racingbets.web.util.ResponseUtil.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 25 Mar 2017
 */
class CrudControllerDelegate<F, D> {
    private static final int ENTITY_LIMIT = 20;

    private CrudService<D> crudService;
    private AccountService accountService;

    private Class<F> formClass;
    private Class<D> dtoClass;
    private String entityType;
    private Locale locale;
    private Validator<F, D> validator;

    CrudControllerDelegate(
            CrudService<D> crudService,
            AccountService accountService,
            Class<F> formClass,
            Class<D> dtoClass,
            String entityType,
            Locale locale,
            Validator<F, D> validator
    ) {
        this.crudService = crudService;
        this.accountService = accountService;
        this.formClass = formClass;
        this.dtoClass = dtoClass;
        this.entityType = entityType;
        this.locale = locale;
        this.validator = validator;
    }

    private ResponseBuilder<D> createResponseBuilder(HttpServletResponse resp) {
        return ResponseUtil.createResponseBuilder(resp, locale, entityType);
    }

    void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<D> responseBuilder = createResponseBuilder(resp);
        try {
            String token = getJwtToken(req);
            if (token != null && accountService.isInRole(token, Role.ADMIN)) {
                F form = getObject(req, formClass);

                validator.validate(form, responseBuilder);

                if (responseBuilder.hasErrors()) {
                    resp.setStatus(SC_UNPROCESSABLE_ENTITY);
                    writeToResponse(resp, responseBuilder.buildErrorResponse());
                } else {
                    Mapper beanMapper = DozerBeanMapperSingletonWrapper.getInstance();
                    D dto = beanMapper.map(form, dtoClass);

                    crudService.create(dto);

                    responseBuilder.addToResult(dto);
                    resp.setStatus(HttpServletResponse.SC_CREATED);

                    writeToResponse(resp, responseBuilder.buildSuccessResponse());
                }
            } else {
                permissionDenied(resp, responseBuilder, locale);
            }
        } catch (JsonParseException e) {
            invalidRequest(resp, responseBuilder, locale);
        }
    }

    void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<D> responseBuilder = createResponseBuilder(resp);
        try {
            String token = getJwtToken(req);
            if (token != null && accountService.isInRole(token, Role.ADMIN)) {
                F form = getObject(req, formClass);

                validator.validate(form, responseBuilder);

                if (responseBuilder.hasErrors()) {
                    resp.setStatus(SC_UNPROCESSABLE_ENTITY);
                    writeToResponse(resp, responseBuilder.buildErrorResponse());
                } else {
                    D dto = map(form, dtoClass);

                    crudService.update(dto);
                    responseBuilder.addToResult(dto);

                    resp.setStatus(HttpServletResponse.SC_ACCEPTED);

                    writeToResponse(resp, responseBuilder.buildSuccessResponse());
                }
            } else {
                permissionDenied(resp, responseBuilder, locale);
            }
        } catch (JsonParseException e) {
            invalidRequest(resp, responseBuilder, locale);
        }
    }

    void findById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<D> responseBuilder = createResponseBuilder(resp);
        ControllerUtil.find(req, resp, responseBuilder, locale, crudService::find);
    }

    void find(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String query = req.getParameter("query");
        int page = getPageFromRequest(req);
        Pager pager = new PagerImpl(ENTITY_LIMIT, page);
        ResponseBuilder<D> responseBuilder = createResponseBuilder(resp);
        List<D> entities;

        if (query != null) {
            entities = crudService.search(query, pager);
        } else {
            entities = crudService.findAll(pager);
        }

        responseBuilder.addAllToResult(entities);
        responseBuilder.setCount(pager.getCount());

        resp.setStatus(HttpServletResponse.SC_FOUND);
        writeToResponse(resp, responseBuilder.buildSuccessResponse());
    }

    void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<D> responseBuilder = createResponseBuilder(resp);
        ControllerUtil.delete(req, resp, responseBuilder, accountService, locale, crudService::delete);
    }
}
