package com.room414.racingbets.web.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.room414.racingbets.bll.abstraction.infrastructure.CheckResult;
import com.room414.racingbets.bll.abstraction.infrastructure.jwt.Jwt;
import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.MessageService;
import com.room414.racingbets.bll.abstraction.services.UserService;
import com.room414.racingbets.bll.dto.entities.UserDto;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;
import com.room414.racingbets.web.model.builders.ResponseBuilder;
import com.room414.racingbets.web.model.enums.ErrorCode;
import com.room414.racingbets.web.model.viewmodels.Error;
import com.room414.racingbets.web.model.viewmodels.RegistrationForm;
import com.room414.racingbets.web.model.viewmodels.Token;
import com.room414.racingbets.web.util.ResponseUtil;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.room414.racingbets.web.util.RequestUtil.getBasicAuthFromRequest;
import static com.room414.racingbets.web.util.ResponseUtil.*;
import static com.room414.racingbets.web.util.ValidatorUtil.*;


/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class AccountController {
    private static final String ENTITY_TYPE = "User";
    private static final String TOKEN_TYPE = "Token";
    private static final String REFRESH_TOKEN_PARAM_NAME = "Refresh-token";

    private AccountService accountService;
    private UserService userService;
    private MessageService messageService;

    private Locale locale;

    public AccountController(
            AccountService accountService,
            UserService userService,
            MessageService messageService,
            Locale locale
    ) {
        this.accountService = accountService;
        this.userService = userService;
        this.messageService = messageService;
        this.locale = locale;
    }

    private <T> ResponseBuilder<T> createResponseBuilder(HttpServletResponse resp) {
        return ResponseUtil.createResponseBuilder(resp, locale, ENTITY_TYPE);
    }

    private Token createToken(UserDto user) {
        Jwt jwt = accountService.getToken(user);
        long expires = jwt.getExpire();
        String accessToken = accountService.getToken(jwt);
        String refreshToken = accountService.getRefreshToken(user.getId());
        return new Token(accessToken, expires, refreshToken);
    }

    private <T> void validate(RegistrationForm form, ResponseBuilder<T> responseBuilder) {
        notNull(form.getFirstName(), responseBuilder, locale, "firstName", ENTITY_TYPE);
        notNull(form.getLastName(), responseBuilder, locale, "lastName", ENTITY_TYPE);
        notNull(form.getLogin(), responseBuilder, locale, "login", ENTITY_TYPE);
        notNull(form.getEmail(), responseBuilder, locale, "email", ENTITY_TYPE);
        notNull(form.getPassword(), responseBuilder, locale, "password", ENTITY_TYPE);

        validateString(form.getFirstName(), responseBuilder, locale, "firstName", ENTITY_TYPE);
        validateString(form.getLastName(), responseBuilder, locale, "lastName", ENTITY_TYPE);
        validateString(form.getLogin(), responseBuilder, locale, "login", ENTITY_TYPE);
        validateString(form.getEmail(), responseBuilder, locale, "email", ENTITY_TYPE);
        validateString(form.getPassword(), responseBuilder, locale, "password", ENTITY_TYPE);

        validateStringLength(form.getFirstName(), 1, 45, responseBuilder, locale, "firstName", ENTITY_TYPE);
        validateStringLength(form.getLastName(), 1, 45,responseBuilder, locale, "lastName", ENTITY_TYPE);
        validateStringLength(form.getLogin(), 6, 45,responseBuilder, locale, "login", ENTITY_TYPE);
        validateStringLength(form.getEmail(), 6, 45,responseBuilder, locale, "email", ENTITY_TYPE);
        validateStringLength(form.getPassword(), 6, 45,responseBuilder, locale, "password", ENTITY_TYPE);

        validateEmail(form.getEmail(), responseBuilder, locale, "email", ENTITY_TYPE);

        validateEmilLoginUniqueness(form, responseBuilder);
    }
    
    private <T> void validateEmilLoginUniqueness(RegistrationForm form, ResponseBuilder<T> responseBuilder) {
        CheckResult checkResult = userService.checkLoginAndEmail(form.getLogin(), form.getEmail());

        if (checkResult.isLoginExists()) {
            String message = ResourceBundle.getBundle(ERROR_MESSAGE_BUNDLE, locale).getString("uniqueness.login");
            Error error = new Error(ErrorCode.UNIQUENESS_VIOLATION_ERROR, message, ENTITY_TYPE, "login");
            responseBuilder.addToErrors(error);
        }

        if (checkResult.isEmailExists()) {
            String message = ResourceBundle.getBundle(ERROR_MESSAGE_BUNDLE, locale).getString("uniqueness.email");
            Error error = new Error(ErrorCode.UNIQUENESS_VIOLATION_ERROR, message, ENTITY_TYPE, "email");
            responseBuilder.addToErrors(error);
        }
    }

    /**
     * POST: account/register
     */
    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<Token> responseBuilder = createResponseBuilder(resp);
        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            RegistrationForm form = jsonMapper.readValue(
                    req.getReader(),
                    RegistrationForm.class
            );

            validate(form, responseBuilder);

            if (responseBuilder.hasErrors()) {
                resp.setStatus(UNPROCESSABLE_ENTITY);
                writeToResponse(resp, responseBuilder.buildErrorResponse());
            } else {
                Mapper beanMapper = DozerBeanMapperSingletonWrapper.getInstance();
                UserDto dto = beanMapper.map(form, UserDto.class);

                userService.create(dto);
                messageService.sendConfirmMessage(dto, accountService.getConfirmToken(dto.getId()));

                responseBuilder.addToResult(createToken(dto));
                resp.setStatus(HttpServletResponse.SC_CREATED);

                writeToResponse(resp, responseBuilder.buildSuccessResponse());
            }
        } catch (JsonParseException e) {
            invalidRequest(resp, responseBuilder, locale);
        }
    }

    /**
     * PUT: account/login
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Pair<String, String> credentials = getBasicAuthFromRequest(req);
        ResponseBuilder<Token> responseBuilder = createResponseBuilder(resp);

        if (credentials == null) {
            String message = ResourceBundle.getBundle(ERROR_MESSAGE_BUNDLE, locale).getString("invalid.auth");
            invalidRequest(resp, responseBuilder, message);
            return;
        }

        UserDto user = userService.findByLoginPassword(credentials.getFirstElement(), credentials.getSecondElement());

        if (user == null) {
            String message = ResourceBundle.getBundle(ERROR_MESSAGE_BUNDLE, locale).getString("login");
            Error error = new Error(ErrorCode.OBJECT_NOT_FOUND, message, ENTITY_TYPE, null);
            responseBuilder.addToErrors(error);
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writeToResponse(resp, responseBuilder.buildErrorResponse());
            return;
        }

        responseBuilder.addToResult(createToken(user));
        writeToResponse(resp, responseBuilder.buildSuccessResponse());
    }

    /**
     * PUT: account/refresh
     */
    public void refresh(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String refreshToken = req.getParameter(REFRESH_TOKEN_PARAM_NAME);
        ResponseBuilder<Token> responseBuilder = createResponseBuilder(resp);

        if (refreshToken == null) {
            String message = ResourceBundle.getBundle(ERROR_MESSAGE_BUNDLE, locale).getString("invalid.refresh.param");
            invalidRequest(resp, responseBuilder, message);
            return;
        }

        long id = accountService.getIdByRefreshToken(refreshToken);

        if (id == 0) {
            String message = ResourceBundle.getBundle(ERROR_MESSAGE_BUNDLE, locale).getString("invalid.refresh");
            Error error = new Error(ErrorCode.INVALID_ARGUMENT, message, ENTITY_TYPE, null);
            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            responseBuilder.addToErrors(error);
            writeToResponse(resp, responseBuilder.buildErrorResponse());
            return;
        }

        UserDto user = userService.find(id);

        if (user == null) {
            String message = ResourceBundle.getBundle(ERROR_MESSAGE_BUNDLE, locale).getString("login");
            Error error = new Error(ErrorCode.OBJECT_NOT_FOUND, message, ENTITY_TYPE, null);
            responseBuilder.addToErrors(error);
            writeToResponse(resp, responseBuilder.buildErrorResponse());
            return;
        }

        responseBuilder.addToResult(createToken(user));
        writeToResponse(resp, responseBuilder.buildSuccessResponse());
    }

    /**
     * PUT: account/roles
     */
    public void setRoles(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * PUT: account/add/money
     */
    public void addMoney(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * PUT: account/confirm
     */
    public void confirmEmail(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: account/?page={page}
     */
    public void getAll(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: account/search/?query={loginPart};page={page}
     */
    public void search(HttpServletRequest req, HttpServletResponse resp) {

    }

    /**
     * GET: account/{id}
     */
    public void find(HttpServletRequest req, HttpServletResponse resp) {

    }
}
