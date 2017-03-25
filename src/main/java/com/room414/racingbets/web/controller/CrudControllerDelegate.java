package com.room414.racingbets.web.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.CrudService;
import com.room414.racingbets.bll.dto.entities.HorseDto;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.web.infrastructure.PagerImpl;
import com.room414.racingbets.web.infrastructure.Validator;
import com.room414.racingbets.web.model.builders.ResponseBuilder;
import com.room414.racingbets.web.util.ResponseUtil;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.room414.racingbets.web.util.RequestUtil.getIdFromRequest;
import static com.room414.racingbets.web.util.RequestUtil.getPageFromRequest;
import static com.room414.racingbets.web.util.RequestUtil.getTokenFromRequest;
import static com.room414.racingbets.web.util.ResponseUtil.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 25 Mar 2017
 */
public class CrudControllerDelegate<F, D> {
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

    /**
     * POST: /horse
     */
    public void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<D> responseBuilder = createResponseBuilder(resp);
        try {
            String token = getTokenFromRequest(req);
            if (accountService.isInRole(token, Role.ADMIN)) {
                ObjectMapper jsonMapper = new ObjectMapper();
                F form = jsonMapper.readValue(
                        req.getReader(),
                        formClass
                );

                validator.validate(form, responseBuilder);

                if (responseBuilder.hasErrors()) {
                    resp.setStatus(UNPROCESSABLE_ENTITY);
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
            invalidRequestBody(resp, responseBuilder, locale);
        }
    }

    /**
     * PUT: /horse
     */
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<D> responseBuilder = createResponseBuilder(resp);
        try {
            String token = getTokenFromRequest(req);
            if (accountService.isInRole(token, Role.ADMIN)) {
                ObjectMapper jsonMapper = new ObjectMapper();
                F form = jsonMapper.readValue(
                        req.getReader(),
                        formClass
                );

                validator.validate(form, responseBuilder);

                if (responseBuilder.hasErrors()) {
                    resp.setStatus(UNPROCESSABLE_ENTITY);
                    writeToResponse(resp, responseBuilder.buildErrorResponse());
                } else {
                    Mapper beanMapper = DozerBeanMapperSingletonWrapper.getInstance();
                    D dto = beanMapper.map(form, dtoClass);

                    crudService.update(dto);
                    responseBuilder.addToResult(dto);

                    resp.setStatus(HttpServletResponse.SC_ACCEPTED);

                    writeToResponse(resp, responseBuilder.buildSuccessResponse());
                }
            } else {
                permissionDenied(resp, responseBuilder, locale);
            }
        } catch (JsonParseException e) {
            invalidRequestBody(resp, responseBuilder, locale);
        }
    }

    /**
     * GET: /horse/{id}
     */
    public void findById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<D> responseBuilder = createResponseBuilder(resp);
        long id = getIdFromRequest(req);

        responseBuilder.addToResult(crudService.find(id));

        resp.setStatus(HttpServletResponse.SC_FOUND);
        writeToResponse(resp, responseBuilder.buildSuccessResponse());
    }

    /**
     * GET: /horse?query={query};page={page}
     */
    public void find(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String query = req.getParameter("query");
        int page = getPageFromRequest(req);
        Pager pager = new PagerImpl(ENTITY_LIMIT, page);
        ResponseBuilder<D> responseBuilder = createResponseBuilder(resp);
        List<D> horses;

        if (query != null) {
            horses = crudService.search(query, pager);
        } else {
            horses = crudService.findAll(pager);
        }

        responseBuilder.addAllToResult(horses);
        responseBuilder.setCount(pager.getCount());

        resp.setStatus(HttpServletResponse.SC_FOUND);
        writeToResponse(resp, responseBuilder.buildSuccessResponse());
    }

    /**
     * DELETE: horse/{id}
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<D> responseBuilder = createResponseBuilder(resp);
        String token = getTokenFromRequest(req);
        if (accountService.isInRole(token, Role.ADMIN)) {
            long id = getIdFromRequest(req);

            crudService.delete(id);

            resp.setStatus(HttpServletResponse.SC_OK);

            writeToResponse(resp, responseBuilder.buildSuccessResponse());
        } else {
            permissionDenied(resp, responseBuilder, locale);
        }
    }

}
