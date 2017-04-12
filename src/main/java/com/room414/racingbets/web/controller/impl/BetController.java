package com.room414.racingbets.web.controller.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.room414.racingbets.bll.abstraction.infrastructure.BetError;
import com.room414.racingbets.bll.abstraction.infrastructure.jwt.Jwt;
import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.BetService;
import com.room414.racingbets.bll.dto.entities.BetDto;
import com.room414.racingbets.bll.dto.entities.OddsDto;
import com.room414.racingbets.dal.abstraction.exception.InvalidIdException;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.web.model.builders.ResponseBuilder;
import com.room414.racingbets.web.model.enums.ErrorCode;
import com.room414.racingbets.web.model.forms.BetForm;
import com.room414.racingbets.web.model.infrastructure.PagerImpl;
import com.room414.racingbets.web.model.viewmodels.Error;
import com.room414.racingbets.web.model.viewmodels.MakeBetResponse;
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
import static com.room414.racingbets.web.util.RequestUtil.getJwtToken;
import static com.room414.racingbets.web.util.RequestUtil.getObject;
import static com.room414.racingbets.web.util.RequestUtil.getPageFromRequest;
import static com.room414.racingbets.web.util.ResponseUtil.*;
import static com.room414.racingbets.web.util.ValidatorUtil.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class BetController {
    private static final String ENTITY_TYPE = "Bet";
    private static final int ENTITY_LIMIT = 20;

    private BetService betService;
    private AccountService accountService;

    private Locale locale;

    public BetController(
            BetService betService,
            AccountService accountService,
            Locale locale
    ) {
        this.betService = betService;
        this.accountService = accountService;
        this.locale = locale;
    }

    private <T> void validate(BetForm form, ResponseBuilder<T> responseBuilder) {
        validateRange(form.getRaceId(), 1, Long.MAX_VALUE, responseBuilder, locale, "raceId", ENTITY_TYPE);
        validateRange(form.getUser(), 1, Long.MAX_VALUE, responseBuilder, locale, "user", ENTITY_TYPE);

        notNull(form.getBetSize(), responseBuilder, locale, "betSize", ENTITY_TYPE);
        notNull(form.getBetType(), responseBuilder, locale, "betType", ENTITY_TYPE);
        notNull(form.getParticipants(), responseBuilder, locale, "betType", ENTITY_TYPE);
    }

    private <T> ResponseBuilder<T> createResponseBuilder(HttpServletResponse resp) {
        return ResponseUtil.createResponseBuilder(resp, locale, ENTITY_TYPE);
    }

    private Error errorByBetError(BetError error) {
        String message;

        switch (error.getErrorType()) {
            case BET_IS_TOO_SMALL:
                message = ResourceBundle
                        .getBundle(ERROR_MESSAGE_BUNDLE, locale)
                        .getString("invalid.bet.size");

                return new Error(ErrorCode.INVALID_ARGUMENT, message, ENTITY_TYPE, error.getField());

            case INVALID_BET_TYPE:
                message = ResourceBundle
                        .getBundle(ERROR_MESSAGE_BUNDLE, locale)
                        .getString("invalid.bet.type");

                return new Error(ErrorCode.INVALID_ARGUMENT, message, ENTITY_TYPE, error.getField());

            case NOT_ENOUGH_MONEY:
                message = ResourceBundle
                        .getBundle(ERROR_MESSAGE_BUNDLE, locale)
                        .getString("not.enough.money");

                return new Error(ErrorCode.INVALID_ARGUMENT, message, ENTITY_TYPE, error.getField());

            case UNCONFIRMED_EMAIL:
                message = ResourceBundle
                        .getBundle(ERROR_MESSAGE_BUNDLE, locale)
                        .getString("email.not.confirmed");

                return new Error(ErrorCode.INVALID_ARGUMENT, message, ENTITY_TYPE, error.getField());

            case INVALID_RACE_STATUS:
                message = ResourceBundle
                        .getBundle(ERROR_MESSAGE_BUNDLE, locale)
                        .getString("race.started");

                return new Error(ErrorCode.INVALID_ARGUMENT, message, ENTITY_TYPE, error.getField());

            default:
                throw new IllegalArgumentException("Unknown error type " + error.getErrorType());
        }
    }

    private <T> Consumer<BetError> betErrorConsumer(ResponseBuilder<T> builder) {
        return e -> {
            Error error = errorByBetError(e);
            builder.addToErrors(error);
        };
    }

    /**
     * POST: /bet
     */
    public void makeBet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<MakeBetResponse> responseBuilder = createResponseBuilder(resp);
        try {
            String token = getJwtToken(req);
            if (token != null && accountService.isInRole(token, Role.HANDICAPPER)) {
                BetForm form = getObject(req, BetForm.class);

                validate(form, responseBuilder);

                if (responseBuilder.hasErrors()) {
                    resp.setStatus(SC_UNPROCESSABLE_ENTITY);
                    writeToResponse(resp, responseBuilder.buildErrorResponse());
                    return;
                }

                BetDto dto = map(form, BetDto.class);

                BigDecimal balance = betService.makeBet(dto, betErrorConsumer(responseBuilder));

                if (responseBuilder.hasErrors()) {
                    resp.setStatus(SC_UNPROCESSABLE_ENTITY);
                    writeToResponse(resp, responseBuilder.buildErrorResponse());
                    return;
                }

                String message = ResourceBundle.getBundle(SUCCESS_MESSAGE_BUNDLE, locale)
                        .getString("make.bet");

                MakeBetResponse response = new MakeBetResponse(message, balance);

                responseBuilder.addToResult(response);
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                writeToResponse(resp, responseBuilder.buildSuccessResponse());
            } else {
                permissionDenied(resp, responseBuilder, locale);
            }
        } catch (JsonParseException | JsonMappingException e) {
            invalidRequest(resp, responseBuilder, locale);
        } catch (InvalidIdException e) {
            invalidId(resp, responseBuilder, locale);
        }
    }

    /**
     * POST: /bet/odds
     */
    public void getOdds(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<OddsDto> responseBuilder = createResponseBuilder(resp);
        try {
            BetForm form = getObject(req, BetForm.class);

            validate(form, responseBuilder);

            if (responseBuilder.hasErrors()) {
                resp.setStatus(SC_UNPROCESSABLE_ENTITY);
                writeToResponse(resp, responseBuilder.buildErrorResponse());
                return;
            }

            BetDto dto = map(form, BetDto.class);

            OddsDto odds = betService.getOdds(dto, betErrorConsumer(responseBuilder));

            if (responseBuilder.hasErrors()) {
                resp.setStatus(SC_UNPROCESSABLE_ENTITY);
                writeToResponse(resp, responseBuilder.buildErrorResponse());
                return;
            }

            responseBuilder.addToResult(odds);
            writeOk(resp, responseBuilder);

        } catch (JsonParseException | InvalidFormatException e) {
            invalidRequest(resp, responseBuilder, locale);
        }
    }


    /**
     * GET: /bet
     */
    public void getBets(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<BetDto> responseBuilder = createResponseBuilder(resp);
        Jwt token = accountService.getToken(getJwtToken(req));

        if (accountService.isValid(token)) {
            long id = token.getUserId();
            int page = getPageFromRequest(req);
            Pager pager = new PagerImpl(ENTITY_LIMIT, page);

            List<BetDto> result = betService.getBetsByUser(id, pager);

            responseBuilder.addAllToResult(result);
            responseBuilder.setPager(pager);
            resp.setStatus(HttpServletResponse.SC_OK);

            writeToResponse(resp, responseBuilder.buildSuccessResponse());
        } else {
            permissionDenied(resp, responseBuilder, locale);
        }
    }
}
