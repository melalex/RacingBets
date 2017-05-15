package com.room414.racingbets.web.controller.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.CrudService;
import com.room414.racingbets.dal.abstraction.exception.InvalidIdException;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.web.model.infrastructure.PagerImpl;
import com.room414.racingbets.web.model.infrastructure.Validator;
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
import java.util.ResourceBundle;

import static com.room414.racingbets.web.util.ControllerUtil.map;
import static com.room414.racingbets.web.util.RequestUtil.*;
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

    private <T> ResponseBuilder<T> createResponseBuilder(HttpServletResponse resp) {
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
                    ResponseBuilder<String> writeCommandResponseBuilder = createResponseBuilder(resp);
                    String message = ResourceBundle.getBundle(SUCCESS_MESSAGE_BUNDLE, locale)
                            .getString("entity.created");
                    Mapper beanMapper = DozerBeanMapperSingletonWrapper.getInstance();
                    D dto = beanMapper.map(form, dtoClass);

                    crudService.create(dto);

                    writeCommandResponseBuilder.addToResult(message);

                    resp.setStatus(HttpServletResponse.SC_CREATED);

                    writeToResponse(resp, writeCommandResponseBuilder.buildSuccessResponse());
                }
            } else {
                permissionDenied(resp, responseBuilder, locale);
            }
        } catch (JsonParseException | JsonMappingException e) {
            invalidRequest(resp, responseBuilder, locale);
        }  catch (InvalidIdException e) {
            invalidId(resp, responseBuilder, locale);
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
                    ResponseBuilder<String> writeCommandResponseBuilder = createResponseBuilder(resp);
                    String message = ResourceBundle.getBundle(SUCCESS_MESSAGE_BUNDLE, locale)
                            .getString("entity.updated");
                    D dto = map(form, dtoClass);

                    long id = getIdFromRequest(req);
                    if (id <= 0) {
                        crudService.update(dto);
                    } else {
                        crudService.update(id, dto);
                    }

                    writeCommandResponseBuilder.addToResult(message);

                    resp.setStatus(HttpServletResponse.SC_ACCEPTED);

                    writeToResponse(resp, writeCommandResponseBuilder.buildSuccessResponse());
                }
            } else {
                permissionDenied(resp, responseBuilder, locale);
            }
        } catch (JsonParseException | JsonMappingException e) {
            invalidRequest(resp, responseBuilder, locale);
        }  catch (InvalidIdException e) {
            invalidId(resp, responseBuilder, locale);
        }
    }

    void find(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<D> responseBuilder = createResponseBuilder(resp);
        ControllerUtil.find(req, resp, responseBuilder, locale, crudService::find);
    }

    void search(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        responseBuilder.setPager(pager);

        resp.setStatus(HttpServletResponse.SC_OK);
        writeToResponse(resp, responseBuilder.buildSuccessResponse());
    }

    void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<String> responseBuilder = createResponseBuilder(resp);
        ControllerUtil.delete(req, resp, responseBuilder, accountService, locale, crudService::delete);
    }
}
