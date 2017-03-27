package com.room414.racingbets.web.controller.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.BetService;
import com.room414.racingbets.bll.abstraction.services.RaceService;
import com.room414.racingbets.bll.abstraction.services.UserService;
import com.room414.racingbets.bll.dto.entities.BetDto;
import com.room414.racingbets.bll.dto.entities.OddsDto;
import com.room414.racingbets.bll.dto.entities.RaceDto;
import com.room414.racingbets.bll.dto.entities.UserDto;
import com.room414.racingbets.dal.domain.enums.RaceStatus;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.web.model.builders.ResponseBuilder;
import com.room414.racingbets.web.model.enums.ErrorCode;
import com.room414.racingbets.web.model.forms.BetForm;
import com.room414.racingbets.web.model.viewmodels.Error;
import com.room414.racingbets.web.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static com.room414.racingbets.web.util.ControllerUtil.map;
import static com.room414.racingbets.web.util.RequestUtil.getJwtToken;
import static com.room414.racingbets.web.util.RequestUtil.getObject;
import static com.room414.racingbets.web.util.ResponseUtil.*;
import static com.room414.racingbets.web.util.ValidatorUtil.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class BetController {
    private static final String ENTITY_TYPE = "Bet";

    private BetService betService;
    private AccountService accountService;
    private UserService userService;
    private RaceService raceService;

    private Locale locale;

    public BetController(
            BetService betService,
            AccountService accountService,
            UserService userService,
            RaceService raceService,
            Locale locale
    ) {
        this.betService = betService;
        this.accountService = accountService;
        this.userService = userService;
        this.raceService = raceService;
        this.locale = locale;
    }

    private boolean isValidBetType(BetForm bet) {
        Map<Integer, Long> participants = bet.getParticipants();

        switch (bet.getBetType()) {
            case SHOW:
                return participants.size() == 3
                        && participants.get(1) != null
                        && participants.get(2) != null
                        && participants.get(3) != null;
            case PLACE:
                return participants.size() == 2
                        && participants.get(1) != null
                        && participants.get(2) != null;
            case WIN:
                return participants.size() == 1
                        && participants.get(1) != null;
            case QUINELLA:
                return participants.size() == 2
                        && participants.get(1) != null
                        && participants.get(2) != null;
            case EXACTA:
                return participants.size() == 2
                        && participants.get(1) != null
                        && participants.get(2) != null;
            case TRIFECTA:
                return participants.size() == 3
                        && participants.get(1) != null
                        && participants.get(2) != null
                        && participants.get(3) != null;
            case SUPERFECTA:
                return participants.size() == 4
                        && participants.get(1) != null
                        && participants.get(2) != null
                        && participants.get(3) != null
                        && participants.get(4) != null;
            default:
                return false;
        }
    }

    private <T> void validate(BetForm form, ResponseBuilder<T> responseBuilder) {
        validateFields(form, responseBuilder);
        validateLogic(form, responseBuilder);
    }

    private <T> void validateFields(BetForm form, ResponseBuilder<T> responseBuilder) {
        validateRange(form.getRaceId(), 1, Long.MAX_VALUE, responseBuilder, locale, "raceId", ENTITY_TYPE);
        validateRange(form.getUser(), 1, Long.MAX_VALUE, responseBuilder, locale, "user", ENTITY_TYPE);

        notNull(form.getBetSize(), responseBuilder, locale, "betSize", ENTITY_TYPE);
        notNull(form.getBetType(), responseBuilder, locale, "betType", ENTITY_TYPE);
        notNull(form.getParticipants(), responseBuilder, locale, "betType", ENTITY_TYPE);
    }

    private void validateLogic(BetForm form, ResponseBuilder responseBuilder) {
        if (!isValidBetType(form)) {
            String message = ResourceBundle.getBundle(ERROR_MESSAGE_BUNDLE, locale).getString("invalid.bet.type");
            Error error = new Error(ErrorCode.INVALID_ARGUMENT, message, ENTITY_TYPE, "betType");
            responseBuilder.addToErrors(error);
        }

        UserDto user = userService.find(form.getUser());

        if (!user.isEmailConfirmed()) {
            String message = ResourceBundle.getBundle(ERROR_MESSAGE_BUNDLE, locale).getString("email.not.confirmed");
            Error error = new Error(ErrorCode.INVALID_ARGUMENT, message, ENTITY_TYPE, "betType");
            responseBuilder.addToErrors(error);
        }

        if (!userService.tryGetMoney(user.getId(), form.getBetSize())) {
            String message = ResourceBundle.getBundle(ERROR_MESSAGE_BUNDLE, locale).getString("not.enough.money");
            Error error = new Error(ErrorCode.INVALID_ARGUMENT, message, ENTITY_TYPE, "betType");
            responseBuilder.addToErrors(error);
        }

        RaceDto race = raceService.find(form.getRaceId());

        if (race.getRaceStatus() != RaceStatus.SCHEDULED) {
            String message = ResourceBundle.getBundle(ERROR_MESSAGE_BUNDLE, locale).getString("race.started");
            Error error = new Error(ErrorCode.INVALID_ARGUMENT, message, ENTITY_TYPE, "betType");
            responseBuilder.addToErrors(error);
        }

        if (race.getMinBet().compareTo(form.getBetSize()) > 0) {
            String message = ResourceBundle.getBundle(ERROR_MESSAGE_BUNDLE, locale).getString("invalid.bet.size");
            Error error = new Error(ErrorCode.INVALID_ARGUMENT, message, ENTITY_TYPE, "raceId");
            responseBuilder.addToErrors(error);

        }
    }


    private <T> ResponseBuilder<T> createResponseBuilder(HttpServletResponse resp) {
        return ResponseUtil.createResponseBuilder(resp, locale, ENTITY_TYPE);
    }

    /**
     * POST: bet/
     */
    public void makeBet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<BetDto> responseBuilder = createResponseBuilder(resp);
        try {
            String token = getJwtToken(req);
            if (token != null && accountService.isInRole(token, Role.HANDICAPPER)) {
                BetForm form = getObject(req, BetForm.class);

                validate(form, responseBuilder);

                if (responseBuilder.hasErrors()) {
                    resp.setStatus(SC_UNPROCESSABLE_ENTITY);
                    writeToResponse(resp, responseBuilder.buildErrorResponse());
                } else {
                    BetDto dto = map(form, BetDto.class);

                    betService.makeBet(dto);
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

    /**
     * POST: bet/odds
     */
    public void getOdds(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<OddsDto> responseBuilder = createResponseBuilder(resp);
        try {
            String token = getJwtToken(req);
            if (token != null && accountService.isInRole(token, Role.HANDICAPPER)) {
                BetForm form = getObject(req, BetForm.class);

                validate(form, responseBuilder);

                if (responseBuilder.hasErrors()) {
                    resp.setStatus(SC_UNPROCESSABLE_ENTITY);
                    writeToResponse(resp, responseBuilder.buildErrorResponse());
                } else {
                    BetDto dto = map(form, BetDto.class);

                    OddsDto odds = betService.getOdds(dto);
                    responseBuilder.addToResult(odds);
                    writeOk(resp, responseBuilder);
                }
            } else {
                permissionDenied(resp, responseBuilder, locale);
            }
        } catch (JsonParseException e) {
            invalidRequest(resp, responseBuilder, locale);
        }
    }
}