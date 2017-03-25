package com.room414.racingbets.web.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.room414.racingbets.bll.abstraction.exceptions.BllException;
import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.HorseService;
import com.room414.racingbets.bll.abstraction.services.ParticipantService;
import com.room414.racingbets.bll.dto.entities.HorseDto;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.web.model.builders.ResponseBuilder;
import com.room414.racingbets.web.util.ControllerUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static com.room414.racingbets.web.util.ControllerUtil.createResponseBuilder;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class HorseController {
    private static final String ENTITY_TYPE = "Horse";

    private HorseService horseService;
    private AccountService accountService;
    private ParticipantService participantService;

    private Locale locale;

    public HorseController(
            HorseService horseService,
            AccountService accountService,
            ParticipantService participantService,
            Locale locale
    ) {
        this.horseService = horseService;
        this.accountService = accountService;
        this.participantService = participantService;
        this.locale = locale;
    }

    private boolean isValid(HorseDto dto) {
        return false;
    }

    /**
     * POST: horse/
     */
    public void create(HttpServletRequest req, HttpServletResponse resp) {
        ResponseBuilder<HorseDto> horseResponseBuilder = createResponseBuilder(resp, locale, ENTITY_TYPE);
        try {
            String token = ControllerUtil.getTokenFromRequest(req);
            if (accountService.isInRole(token, Role.ADMIN)) {
                ObjectMapper jsonMapper = new ObjectMapper();
                HorseDto horse = jsonMapper.readValue(
                        req.getParameter("data"),
                        HorseDto.class
                );

                horseService.create(horse);

                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                // TODO: add logic
            }
        } catch (BllException e) {
            // TODO: add logic
        } catch (JsonParseException e) {
            // TODO: add logic
        } catch (JsonMappingException e) {
            // TODO: add logic
        } catch (IOException e) {
            // TODO: add logic
        }
    }

    /**
     * PUT: horse/{id}
     */
    public void update(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String token = ControllerUtil.getTokenFromRequest(req);
            if (accountService.isInRole(token, Role.ADMIN)) {
                ObjectMapper jsonMapper = new ObjectMapper();
                HorseDto horse = jsonMapper.readValue(
                        req.getParameter("data"),
                        HorseDto.class
                );

                horseService.update(horse);

                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            } else {
                // TODO: add logic
            }
        } catch (BllException e) {
            // TODO: add logic
        } catch (JsonParseException e) {
            // TODO: add logic
        } catch (JsonMappingException e) {
            // TODO: add logic
        } catch (IOException e) {
            // TODO: add logic
        }
    }

    /**
     * GET: horse/{id}
     */
    public void find(HttpServletRequest req, HttpServletResponse resp) {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            ObjectMapper jsonMapper = new ObjectMapper();
            HorseDto horse = jsonMapper.readValue(
                    req.getParameter("data"),
                    HorseDto.class
            );

            HorseDto horseDto = horseService.find(id);

            resp.setStatus(HttpServletResponse.SC_FOUND);

        } catch (BllException e) {
            // TODO: add logic
        } catch (JsonParseException e) {
            // TODO: add logic
        } catch (JsonMappingException e) {
            // TODO: add logic
        } catch (IOException e) {
            // TODO: add logic
        }
    }

    /**
     * GET: horse/search/{namePart}
     */
    public void search(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: horse/all/{page}
     */
    public void findAll(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: horse/participant/{id}
     */
    public void findAsParticipant(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * DELETE: horse/{id}
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) {

    }
}
