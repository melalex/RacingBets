package com.room414.racingbets.web.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.room414.racingbets.bll.abstraction.infrastructure.FilterParamsBuilder;
import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.RaceService;
import com.room414.racingbets.bll.dto.entities.ParticipantDto;
import com.room414.racingbets.bll.dto.entities.RaceDto;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.web.infrastructure.PagerImpl;
import com.room414.racingbets.web.model.builders.ResponseBuilder;
import com.room414.racingbets.web.util.ControllerUtil;
import com.room414.racingbets.web.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import static com.room414.racingbets.web.util.RequestUtil.getIdFromRequest;
import static com.room414.racingbets.web.util.RequestUtil.getPageFromRequest;
import static com.room414.racingbets.web.util.RequestUtil.getTokenFromRequest;
import static com.room414.racingbets.web.util.ResponseUtil.*;
import static com.room414.racingbets.web.util.ValidatorUtil.*;
import static com.room414.racingbets.web.util.ValidatorUtil.STRING_MAX_LENGTH;


/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class RaceController {
    private static final String ENTITY_TYPE = "Race";
    private static final int ENTITY_LIMIT = 20;

    private RaceService raceService;
    private AccountService accountService;
    private Locale locale;

    public RaceController(RaceService raceService, AccountService accountService, Locale locale) {
        this.raceService = raceService;
        this.accountService = accountService;
        this.locale = locale;
    }

    private ResponseBuilder<RaceDto> createResponseBuilder(HttpServletResponse resp) {
        return ResponseUtil.createResponseBuilder(resp, locale, ENTITY_TYPE);
    }

    // TODO: add more validation rules
    // TODO: Race view model
    private void validate(RaceDto form, ResponseBuilder<RaceDto> responseBuilder) {
        notNull(form.getName(), responseBuilder, locale, "name", ENTITY_TYPE);
        notNull(form.getRacecourse(), responseBuilder, locale, "racecourse", ENTITY_TYPE);
        notNull(form.getStart(), responseBuilder, locale, "start", ENTITY_TYPE);
        notNull(form.getMinBet(), responseBuilder, locale, "minBet", ENTITY_TYPE);
        notNull(form.getRaceType(), responseBuilder, locale, "raceType", ENTITY_TYPE);

        validateString(form.getName(), responseBuilder, locale, "name", ENTITY_TYPE);

        validateStringLength(form.getName(), STRING_MIN_LENGTH, STRING_MAX_LENGTH, responseBuilder, locale, "name", ENTITY_TYPE);

        for (ParticipantDto participantDto : form.getParticipants()) {
            validate(participantDto, responseBuilder);
        }
    }

    private void validate(ParticipantDto form, ResponseBuilder<RaceDto> responseBuilder) {
        notNull(form.getHorse(), responseBuilder, locale, "horse", ENTITY_TYPE);
        notNull(form.getJockey(), responseBuilder, locale, "jockey", ENTITY_TYPE);
        notNull(form.getTrainer(), responseBuilder, locale, "trainer", ENTITY_TYPE);
    }

    /**
     * POST: /race
     */
    public void scheduleRace(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<RaceDto> responseBuilder = createResponseBuilder(resp);
        try {
            String token = getTokenFromRequest(req);
            if (token != null && accountService.isInRole(token, Role.ADMIN)) {
                ObjectMapper jsonMapper = new ObjectMapper();
                RaceDto form = jsonMapper.readValue(
                        req.getReader(),
                        RaceDto.class
                );

                validate(form, responseBuilder);

                if (responseBuilder.hasErrors()) {
                    resp.setStatus(UNPROCESSABLE_ENTITY);
                    writeToResponse(resp, responseBuilder.buildErrorResponse());
                } else {
                    raceService.scheduleRace(form);

                    responseBuilder.addToResult(form);
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

    /**
     * PUT: /race/start/{id}
     */
    public void startRace(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<RaceDto> responseBuilder = createResponseBuilder(resp);
        String token = getTokenFromRequest(req);
        if (token != null && accountService.isInRole(token, Role.ADMIN)) {
            long id = getIdFromRequest(req);

            if (id == 0) {
                invalidId(resp, responseBuilder, locale);
            } else {
                raceService.startRace(id);
                writeOk(resp, responseBuilder);
            }
        } else {
            permissionDenied(resp, responseBuilder, locale);
        }
    }

    private void updateRace(HttpServletRequest req, HttpServletResponse resp, Consumer<RaceDto> updater) throws IOException {
        ResponseBuilder<RaceDto> responseBuilder = createResponseBuilder(resp);
        String token = getTokenFromRequest(req);
        if (token != null && accountService.isInRole(token, Role.ADMIN)) {
            ObjectMapper jsonMapper = new ObjectMapper();
            RaceDto form = jsonMapper.readValue(
                    req.getReader(),
                    RaceDto.class
            );

            validate(form, responseBuilder);

            if (responseBuilder.hasErrors()) {
                resp.setStatus(UNPROCESSABLE_ENTITY);
                writeToResponse(resp, responseBuilder.buildErrorResponse());
            } else {
                updater.accept(form);
                responseBuilder.addToResult(form);
                writeOk(resp, responseBuilder);
            }
        } else {
            permissionDenied(resp, responseBuilder, locale);
        }
    }

    /**
     * PUT: /race/reject/{id}
     */
    public void rejectRace(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        updateRace(req, resp, raceService::rejectRace);
    }

    /**
     * PUT: /race/finish/{id}
     */
    public void finishRace(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        updateRace(req, resp, raceService::finishRace);
    }

    /**
     * PUT: /race/{id}
     */
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        updateRace(req, resp, raceService::update);
    }

    /**
     * DELETE: /race/{id}
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ControllerUtil.delete(req, resp, createResponseBuilder(resp), accountService, locale, raceService::delete);
    }

    /**
     * GET: /race/{id}
     */
    public void find(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<RaceDto> responseBuilder = createResponseBuilder(resp);
        ControllerUtil.find(req, resp, responseBuilder, locale, raceService::find);
    }

    /**
     * GET: /race?status={status};
     *            racecourse={racecourse};
     *            horse={horse};
     *            trainer={trainer};
     *            jockey={jockey};
     *            name={name};
     *            date={date};
     *            page={page}
     */
    public void filter(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<RaceDto> responseBuilder = createResponseBuilder(resp);
        FilterParamsBuilder paramsBuilder = raceService
                .filterParamsBuilder()
                .setRaceStatus(req.getParameter("status"))
                .setRacecourseId(req.getParameter("racecourse"))
                .setHorseId(req.getParameter("horse"))
                .setTrainerId(req.getParameter("trainer"))
                .setJockeyId(req.getParameter("jockey"))
                .setName(req.getParameter("name"))
                .setDate(req.getParameter("date"));

        int page = getPageFromRequest(req);

        Pager pager = new PagerImpl(ENTITY_LIMIT, page);

        List<RaceDto> result = raceService.filter(paramsBuilder, pager);

        responseBuilder.addAllToResult(result);
        responseBuilder.setCount(pager.getCount());

        resp.setStatus(HttpServletResponse.SC_FOUND);
        writeToResponse(resp, responseBuilder.buildSuccessResponse());
    }
}
