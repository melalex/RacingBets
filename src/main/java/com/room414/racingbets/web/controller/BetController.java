package com.room414.racingbets.web.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.BetService;
import com.room414.racingbets.bll.dto.entities.BetDto;
import com.room414.racingbets.bll.dto.entities.OddsDto;
import com.room414.racingbets.bll.dto.entities.ParticipantDto;
import com.room414.racingbets.bll.dto.entities.RaceDto;
import com.room414.racingbets.dal.domain.entities.Odds;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.web.model.builders.ResponseBuilder;
import com.room414.racingbets.web.util.ResponseUtil;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static com.room414.racingbets.web.util.RequestUtil.getTokenFromRequest;
import static com.room414.racingbets.web.util.ResponseUtil.*;
import static com.room414.racingbets.web.util.ValidatorUtil.*;
import static com.room414.racingbets.web.util.ValidatorUtil.STRING_MAX_LENGTH;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class BetController {
    private static final String ENTITY_TYPE = "Race";
    private static final int ENTITY_LIMIT = 10;

    private BetService betService;
    private AccountService accountService;
    private Locale locale;

    public BetController(BetService betService, AccountService accountService, Locale locale) {
        this.betService = betService;
        this.accountService = accountService;
        this.locale = locale;
    }

    private <T> void validate(BetDto form, ResponseBuilder<T> responseBuilder) {

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
            String token = getTokenFromRequest(req);
            if (token != null && accountService.isInRole(token, Role.HANDICAPPER)) {
                ObjectMapper jsonMapper = new ObjectMapper();
                BetDto form = jsonMapper.readValue(
                        req.getReader(),
                        BetDto.class
                );

                validate(form, responseBuilder);

                if (responseBuilder.hasErrors()) {
                    resp.setStatus(UNPROCESSABLE_ENTITY);
                    writeToResponse(resp, responseBuilder.buildErrorResponse());
                } else {
                    betService.makeBet(form);
                    responseBuilder.addToResult(form);
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
            String token = getTokenFromRequest(req);
            if (token != null && accountService.isInRole(token, Role.HANDICAPPER)) {
                ObjectMapper jsonMapper = new ObjectMapper();
                BetDto form = jsonMapper.readValue(
                        req.getReader(),
                        BetDto.class
                );

                validate(form, responseBuilder);

                if (responseBuilder.hasErrors()) {
                    resp.setStatus(UNPROCESSABLE_ENTITY);
                    writeToResponse(resp, responseBuilder.buildErrorResponse());
                } else {
                    OddsDto odds = betService.getOdds(form);
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
