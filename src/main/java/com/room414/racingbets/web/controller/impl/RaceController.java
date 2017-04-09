package com.room414.racingbets.web.controller.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.room414.racingbets.bll.abstraction.infrastructure.FilterParamsBuilder;
import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.RaceService;
import com.room414.racingbets.bll.dto.entities.RaceDto;
import com.room414.racingbets.dal.abstraction.exception.InvalidIdException;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.web.model.infrastructure.PagerImpl;
import com.room414.racingbets.web.model.builders.ResponseBuilder;
import com.room414.racingbets.web.model.forms.ParticipantForm;
import com.room414.racingbets.web.model.forms.RaceForm;
import com.room414.racingbets.web.util.ControllerUtil;
import com.room414.racingbets.web.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import static com.room414.racingbets.web.util.ControllerUtil.map;
import static com.room414.racingbets.web.util.RequestUtil.*;
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

    private <T> ResponseBuilder<T> createResponseBuilder(HttpServletResponse resp) {
        return ResponseUtil.createResponseBuilder(resp, locale, ENTITY_TYPE);
    }

    private void validate(RaceForm form, ResponseBuilder<RaceDto> builder) {
        notNull(form.getName(), builder, locale, "name", ENTITY_TYPE);
        notNull(form.getStart(), builder, locale, "start", ENTITY_TYPE);
        notNull(form.getMinBet(), builder, locale, "minBet", ENTITY_TYPE);
        notNull(form.getRaceType(), builder, locale, "raceType", ENTITY_TYPE);
        notNull(form.getParticipants(), builder, locale, "participants", ENTITY_TYPE);
        notNull(form.getPrizes(), builder, locale, "prizes", ENTITY_TYPE);

        validateString(form.getName(), builder, locale, "name", ENTITY_TYPE);

        validateStringLength(form.getName(), STRING_MIN_LENGTH, STRING_MAX_LENGTH, builder, locale, "name", ENTITY_TYPE);

        validateRange(form.getRacecourse(), 1, Integer.MAX_VALUE, builder, locale, "racecourse", ENTITY_TYPE);
        validateRange(form.getRaceClass(), 0, Integer.MAX_VALUE, builder, locale, "raceClass", ENTITY_TYPE);
        validateRange(form.getMinAge(), 0, Integer.MAX_VALUE, builder, locale, "commission", ENTITY_TYPE);
        validateRange(form.getMinRating(), 0, Integer.MAX_VALUE, builder, locale, "commission", ENTITY_TYPE);
        validateRange(form.getMaxRating(), form.getMinRating(), Integer.MAX_VALUE, builder, locale, "commission", ENTITY_TYPE);

        validateRange(form.getCommission(), 0, 1, builder, locale, "commission", ENTITY_TYPE);
        validateRange(form.getDistance(), 0, Double.MAX_VALUE, builder, locale, "distance", ENTITY_TYPE);

        if (form.getMinBet() != null) {
            validateRange(form.getMinBet().doubleValue(), 0, Double.MAX_VALUE, builder, locale, "minBet", ENTITY_TYPE);
        }

        if (form.getParticipants() != null) {
            for (ParticipantForm participantDto : form.getParticipants()) {
                validate(participantDto, builder);
            }
        }

        if (form.getPrizes() != null) {
            BigDecimal bigDecimal;
            for (Integer place : form.getPrizes().keySet()) {
                bigDecimal = form.getPrize(place);
                if (bigDecimal == null) {
                    continue;
                }
                validateRange(bigDecimal.doubleValue(), 0, Double.MAX_VALUE, builder, locale, "prizes", ENTITY_TYPE);
            }
        }
    }

    private void validate(ParticipantForm form, ResponseBuilder<RaceDto> builder) {
        validateRange(form.getNumber(), 0, Integer.MAX_VALUE, builder, locale, "number", ENTITY_TYPE);
        validateRange(form.getHorse(), 1, Integer.MAX_VALUE, builder, locale, "horse", ENTITY_TYPE);
        validateRange(form.getCarriedWeight(), 0, Double.MAX_VALUE, builder, locale, "carriedWeight", ENTITY_TYPE);
        validateRange(form.getTopSpeed(), 0, Integer.MAX_VALUE, builder, locale, "topspeed", ENTITY_TYPE);
        validateRange(form.getOfficialRating(), 0, Integer.MAX_VALUE, builder, locale, "officialRating", ENTITY_TYPE);
        validateRange(form.getOdds(), 0, Double.MAX_VALUE, builder, locale, "odds", ENTITY_TYPE);
        validateRange(form.getJockey(), 1, Integer.MAX_VALUE, builder, locale, "jockey", ENTITY_TYPE);
        validateRange(form.getTrainer(), 1, Integer.MAX_VALUE, builder, locale, "trainer", ENTITY_TYPE);
        validateRange(form.getPlace(), 0, Integer.MAX_VALUE, builder, locale, "place", ENTITY_TYPE);
    }

    /**
     * POST: /race
     */
    public void scheduleRace(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<RaceDto> responseBuilder = createResponseBuilder(resp);
        try {
            String token = getJwtToken(req);
            if (token != null && accountService.isInRole(token, Role.ADMIN)) {
                RaceForm form = getObject(req, RaceForm.class);

                validate(form, responseBuilder);

                if (responseBuilder.hasErrors()) {
                    resp.setStatus(SC_UNPROCESSABLE_ENTITY);
                    writeToResponse(resp, responseBuilder.buildErrorResponse());
                } else {
                    ResponseBuilder<String> writeCommandResponseBuilder = createResponseBuilder(resp);
                    String message = ResourceBundle.getBundle(SUCCESS_MESSAGE_BUNDLE, locale)
                            .getString("race.scheduled");

                    RaceDto dto = map(form, RaceDto.class);

                    raceService.scheduleRace(dto);

                    writeCommandResponseBuilder.addToResult(message);
                    resp.setStatus(HttpServletResponse.SC_CREATED);

                    writeToResponse(resp, writeCommandResponseBuilder.buildSuccessResponse());
                }
            } else {
                permissionDenied(resp, responseBuilder, locale);
            }
        } catch (JsonParseException | InvalidFormatException e) {
            invalidRequest(resp, responseBuilder, locale);
        }  catch (InvalidIdException e) {
            invalidId(resp, responseBuilder, locale);
        }
    }

    /**
     * PUT: /race/start/%d
     */
    public void startRace(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<RaceDto> responseBuilder = createResponseBuilder(resp);
        String token = getJwtToken(req);
        if (token != null && accountService.isInRole(token, Role.ADMIN)) {
            long id = getIdFromRequest(req);

            if (id == 0) {
                invalidId(resp, responseBuilder, locale);
            } else {
                ResponseBuilder<String> writeCommandResponseBuilder = createResponseBuilder(resp);
                String message = ResourceBundle.getBundle(SUCCESS_MESSAGE_BUNDLE, locale)
                        .getString("race.started");

                raceService.startRace(id);

                writeCommandResponseBuilder.addToResult(message);
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);

                writeToResponse(resp, writeCommandResponseBuilder.buildSuccessResponse());
            }
        } else {
            permissionDenied(resp, responseBuilder, locale);
        }
    }

    private void updateRace(HttpServletRequest req, HttpServletResponse resp, Consumer<RaceDto> updater) throws IOException {
        ResponseBuilder<RaceDto> responseBuilder = createResponseBuilder(resp);
        String token = getJwtToken(req);
        try {
            if (token != null && accountService.isInRole(token, Role.ADMIN)) {
                RaceForm form = getObject(req, RaceForm.class);

                validate(form, responseBuilder);

                if (responseBuilder.hasErrors()) {
                    resp.setStatus(SC_UNPROCESSABLE_ENTITY);
                    writeToResponse(resp, responseBuilder.buildErrorResponse());
                } else {
                    ResponseBuilder<String> writeCommandResponseBuilder = createResponseBuilder(resp);
                    String message = ResourceBundle.getBundle(SUCCESS_MESSAGE_BUNDLE, locale)
                            .getString("race.updated");

                    RaceDto dto = map(form, RaceDto.class);

                    updater.accept(dto);

                    writeCommandResponseBuilder.addToResult(message);
                    resp.setStatus(HttpServletResponse.SC_ACCEPTED);

                    writeToResponse(resp, writeCommandResponseBuilder.buildSuccessResponse());
                }
            } else {
                permissionDenied(resp, responseBuilder, locale);
            }
        } catch (JsonParseException | InvalidFormatException e) {
            invalidRequest(resp, responseBuilder, locale);
        }  catch (InvalidIdException e) {
            invalidId(resp, responseBuilder, locale);
        }
    }

    /**
     * PUT: /race/reject/%d
     */
    public void rejectRace(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        updateRace(req, resp, raceService::rejectRace);
    }

    /**
     * PUT: /race/finish/%d
     */
    public void finishRace(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        updateRace(req, resp, raceService::finishRace);
    }

    /**
     * PUT: /race/%d
     */
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        updateRace(req, resp, raceService::update);
    }

    /**
     * DELETE: /race/%d
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ControllerUtil.delete(req, resp, createResponseBuilder(resp), accountService, locale, raceService::delete);
    }

    /**
     * GET: /race/%d
     */
    public void find(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<RaceDto> responseBuilder = createResponseBuilder(resp);
        ControllerUtil.find(req, resp, responseBuilder, locale, raceService::find);
    }

    /**
     * GET: /race?status=%s&racecourse=%d&horse=%d&trainer=%d&jockey=%d&name=%s&date=%d&page=%d
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
        responseBuilder.setPager(pager);

        resp.setStatus(HttpServletResponse.SC_OK);
        writeToResponse(resp, responseBuilder.buildSuccessResponse());
    }
}
