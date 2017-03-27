package com.room414.racingbets.web.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.BetService;
import com.room414.racingbets.bll.abstraction.services.RaceService;
import com.room414.racingbets.bll.abstraction.services.UserService;
import com.room414.racingbets.bll.dto.entities.BetDto;
import com.room414.racingbets.bll.dto.entities.OddsDto;
import com.room414.racingbets.bll.dto.entities.RaceDto;
import com.room414.racingbets.bll.dto.entities.UserDto;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.RaceStatus;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.web.model.builders.ResponseBuilder;
import com.room414.racingbets.web.model.viewmodels.BetForm;
import com.room414.racingbets.web.util.ResponseUtil;
import com.room414.racingbets.web.util.ValidatorUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

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
    private static final String ENTITY_TYPE = "Race";

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

    private void validate(BetForm form, ResponseBuilder responseBuilder) {
        validateFields(form, responseBuilder);
        validateLogic(form, responseBuilder);
    }

    private void validateFields(BetForm form, ResponseBuilder responseBuilder) {
        validateRange(form.getId(), );
    }

    private void validateLogic(BetForm form, ResponseBuilder responseBuilder) {
        if (!isValidBetType(form)) {

        }

        UserDto user = userService.find(form.getId());

        if (!user.isEmailConfirmed()) {

        }

        if (!userService.tryGetMoney(user.getId(), form.getBetSize())) {

        }

        RaceDto race = raceService.find(form.getRaceId());

        if (race.getRaceStatus() != RaceStatus.SCHEDULED) {

        }

        if (race.getMinBet().compareTo(form.getBetSize()) > 0) {

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
