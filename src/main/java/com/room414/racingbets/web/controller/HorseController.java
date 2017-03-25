package com.room414.racingbets.web.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.HorseService;
import com.room414.racingbets.bll.abstraction.services.ParticipantService;
import com.room414.racingbets.bll.dto.entities.HorseDto;
import com.room414.racingbets.bll.dto.entities.RaceParticipantThumbnailDto;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.web.infrastructure.PagerImpl;
import com.room414.racingbets.web.model.builders.ResponseBuilder;
import com.room414.racingbets.web.model.viewmodels.HorseForm;
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
import static com.room414.racingbets.web.util.ValidatorUtil.*;


/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class HorseController {
    private static final String ENTITY_TYPE = "Horse";
    private static final String PARTICIPANT_TYPE = "RaceParticipantThumbnail";
    private static final int HORSE_LIMIT = 20;
    private static final int PARTICIPANT_LIMIT = 10;

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

    private void validate(HorseForm form, ResponseBuilder<HorseDto> responseBuilder) {
        notNull(form.getName(), responseBuilder, locale, "name", ENTITY_TYPE);
        notNull(form.getBirthday(), responseBuilder, locale, "birthday", ENTITY_TYPE);
        notNull(form.getGender(), responseBuilder, locale, "gender", ENTITY_TYPE);

        notNull(form.getOwner(), responseBuilder, locale, "owner", ENTITY_TYPE);
        notNull(form.getTrainer(), responseBuilder, locale, "trainer", ENTITY_TYPE);

        validateString(form.getName(), responseBuilder, locale, "name", ENTITY_TYPE);

        validateForeignKey(form.getOwner(), responseBuilder, locale, "owner", ENTITY_TYPE);
        validateForeignKey(form.getTrainer(), responseBuilder, locale, "trainer", ENTITY_TYPE);

        validateForeignKey(form.getDam(), responseBuilder, locale, "dam", ENTITY_TYPE);
        validateForeignKey(form.getSir(), responseBuilder, locale, "sir", ENTITY_TYPE);
    }

    private ResponseBuilder<HorseDto> createResponseBuilder(HttpServletResponse resp) {
        return ResponseUtil.createResponseBuilder(resp, locale, ENTITY_TYPE);
    }

    private ResponseBuilder<RaceParticipantThumbnailDto> createParticipantResponseBuilder(HttpServletResponse resp) {
        return ResponseUtil.createResponseBuilder(resp, locale, PARTICIPANT_TYPE);
    }

    /**
     * POST: /horse
     */
    public void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<HorseDto> responseBuilder = createResponseBuilder(resp);
        try {
            String token = getTokenFromRequest(req);
            if (accountService.isInRole(token, Role.ADMIN)) {
                ObjectMapper jsonMapper = new ObjectMapper();
                HorseForm form = jsonMapper.readValue(
                        req.getReader(),
                        HorseForm.class
                );

                validate(form, responseBuilder);

                if (responseBuilder.hasErrors()) {
                    resp.setStatus(UNPROCESSABLE_ENTITY);
                    writeToResponse(resp, responseBuilder.buildErrorResponse());
                } else {
                    Mapper beanMapper = DozerBeanMapperSingletonWrapper.getInstance();
                    HorseDto dto = beanMapper.map(form, HorseDto.class);

                    horseService.create(dto);

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
        ResponseBuilder<HorseDto> responseBuilder = createResponseBuilder(resp);
        try {
            String token = getTokenFromRequest(req);
            if (accountService.isInRole(token, Role.ADMIN)) {
                ObjectMapper jsonMapper = new ObjectMapper();
                HorseForm form = jsonMapper.readValue(
                        req.getParameter("data"),
                        HorseForm.class
                );

                validate(form, responseBuilder);

                if (responseBuilder.hasErrors()) {
                    resp.setStatus(UNPROCESSABLE_ENTITY);
                    writeToResponse(resp, responseBuilder.buildErrorResponse());
                } else {
                    Mapper beanMapper = DozerBeanMapperSingletonWrapper.getInstance();
                    HorseDto dto = beanMapper.map(form, HorseDto.class);

                    horseService.update(dto);
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
        ResponseBuilder<HorseDto> responseBuilder = createResponseBuilder(resp);
        long id = getIdFromRequest(req);

        responseBuilder.addToResult(horseService.find(id));

        resp.setStatus(HttpServletResponse.SC_FOUND);
        writeToResponse(resp, responseBuilder.buildSuccessResponse());
    }

    /**
     * GET: /horse
     */
    public void find(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String query = req.getParameter("query");
        int page = getPageFromRequest(req);
        Pager pager = new PagerImpl(HORSE_LIMIT, page);
        ResponseBuilder<HorseDto> responseBuilder = createResponseBuilder(resp);
        List<HorseDto> horses;

        if (query != null) {
            horses = horseService.search(query, pager);
        } else {
            horses = horseService.findAll(pager);
        }

        responseBuilder.addAllToResult(horses);
        responseBuilder.setCount(pager.getCount());

        resp.setStatus(HttpServletResponse.SC_FOUND);
        writeToResponse(resp, responseBuilder.buildSuccessResponse());
    }

    /**
     * GET: /horse/participant/{id}?page={page}
     */
    public void findAsParticipant(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = getIdFromRequest(req);
        int page = getPageFromRequest(req);

        ResponseBuilder<RaceParticipantThumbnailDto> responseBuilder = createParticipantResponseBuilder(resp);
        Pager pager = new PagerImpl(PARTICIPANT_LIMIT, page);

        List<RaceParticipantThumbnailDto> horses = participantService.findByHorse(id, pager);

        responseBuilder.addAllToResult(horses);
        responseBuilder.setCount(pager.getCount());

        resp.setStatus(HttpServletResponse.SC_FOUND);
        writeToResponse(resp, responseBuilder.buildSuccessResponse());
    }

    /**
     * DELETE: horse/{id}
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<HorseDto> responseBuilder = createResponseBuilder(resp);
        long id = getIdFromRequest(req);

        horseService.delete(id);

        resp.setStatus(HttpServletResponse.SC_OK);

        writeToResponse(resp, responseBuilder.buildSuccessResponse());
    }
}
