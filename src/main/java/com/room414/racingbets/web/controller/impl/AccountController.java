package com.room414.racingbets.web.controller.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.room414.racingbets.bll.abstraction.infrastructure.CheckResult;
import com.room414.racingbets.bll.abstraction.infrastructure.jwt.Jwt;
import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.abstraction.services.MessageService;
import com.room414.racingbets.bll.abstraction.services.UserService;
import com.room414.racingbets.bll.dto.entities.UserDto;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.web.model.infrastructure.PagerImpl;
import com.room414.racingbets.web.model.builders.ResponseBuilder;
import com.room414.racingbets.web.model.enums.ErrorCode;
import com.room414.racingbets.web.model.viewmodels.Error;
import com.room414.racingbets.web.model.forms.RegistrationForm;
import com.room414.racingbets.web.model.viewmodels.Token;
import com.room414.racingbets.web.model.viewmodels.UserViewModel;
import com.room414.racingbets.web.util.ControllerUtil;
import com.room414.racingbets.web.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;

import static com.room414.racingbets.web.util.ControllerUtil.map;
import static com.room414.racingbets.web.util.RequestUtil.*;
import static com.room414.racingbets.web.util.ResponseUtil.*;
import static com.room414.racingbets.web.util.ValidatorUtil.*;


/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class AccountController {
    private static final String ENTITY_TYPE = "User";
    private static final int ENTITY_LIMIT = 20;

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
        validateString(form.getPassword(), responseBuilder, locale, "password", ENTITY_TYPE);

        validateStringLength(form.getFirstName(), STRING_MIN_LENGTH, STRING_MAX_LENGTH, responseBuilder, locale, "firstName", ENTITY_TYPE);
        validateStringLength(form.getLastName(), STRING_MIN_LENGTH, STRING_MAX_LENGTH,responseBuilder, locale, "lastName", ENTITY_TYPE);
        validateStringLength(form.getLogin(), AUTH_STRING_MIN_LENGTH, AUTH_STRING_MAX_LENGTH,responseBuilder, locale, "login", ENTITY_TYPE);
        validateStringLength(form.getPassword(), AUTH_STRING_MIN_LENGTH, AUTH_STRING_MAX_LENGTH,responseBuilder, locale, "password", ENTITY_TYPE);

        validateEmail(form.getEmail(), responseBuilder, locale, ENTITY_TYPE);

        validateEmilLoginUniqueness(form, responseBuilder);
    }

    private String getToken(HttpServletRequest req) {
        String[] uri = req.getRequestURI().split("/");
        return uri[uri.length - 1];
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
     * POST: /account/register
     */
    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<Token> responseBuilder = createResponseBuilder(resp);
        try {
            RegistrationForm form = getObject(req, RegistrationForm.class);

            validate(form, responseBuilder);

            if (responseBuilder.hasErrors()) {
                resp.setStatus(SC_UNPROCESSABLE_ENTITY);
                writeToResponse(resp, responseBuilder.buildErrorResponse());
            } else {
                UserDto dto = map(form, UserDto.class);

                userService.create(dto);

                // TODO: Uncomment for sending confirm emails
                // messageService.sendConfirmMessage(dto, accountService.getConfirmToken(dto.getId()));

                responseBuilder.addToResult(createToken(dto));
                resp.setStatus(HttpServletResponse.SC_CREATED);

                writeToResponse(resp, responseBuilder.buildSuccessResponse());
            }
        } catch (JsonParseException | JsonMappingException e) {
            invalidRequest(resp, responseBuilder, locale);
        }
    }

    private void login(HttpServletRequest req,
                       HttpServletResponse resp,
                       Function<UserDto, Boolean> rolesCheck) throws IOException {

        Pair<String, String> credentials = getBasicAuthFromRequest(req);
        ResponseBuilder<Token> responseBuilder = createResponseBuilder(resp);

        if (credentials == null) {
            String message = ResourceBundle.getBundle(ERROR_MESSAGE_BUNDLE, locale).getString("invalid.auth");
            invalidRequest(resp, responseBuilder, message);
            return;
        }

        UserDto user = userService.findByLoginPassword(credentials.getFirstElement(), credentials.getSecondElement());

        if (user == null || !rolesCheck.apply(user)) {
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

    private void refresh(HttpServletRequest req,
                         HttpServletResponse resp,
                         Function<UserDto, Boolean> rolesCheck) throws IOException {
        String token = getToken(req);

        ResponseBuilder<Token> responseBuilder = createResponseBuilder(resp);

        if (token == null) {
            String message = ResourceBundle.getBundle(ERROR_MESSAGE_BUNDLE, locale).getString("invalid.refresh.param");
            invalidRequest(resp, responseBuilder, message);
            return;
        }

        long id = accountService.getIdByRefreshToken(token);

        if (id == 0) {
            String message = ResourceBundle.getBundle(ERROR_MESSAGE_BUNDLE, locale).getString("invalid.refresh");
            Error error = new Error(ErrorCode.INVALID_ARGUMENT, message, ENTITY_TYPE, null);
            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            responseBuilder.addToErrors(error);
            writeToResponse(resp, responseBuilder.buildErrorResponse());
            return;
        }

        accountService.deleteRefreshToken(token);

        UserDto user = userService.find(id);

        if (user == null || !rolesCheck.apply(user)) {
            String message = ResourceBundle.getBundle(ERROR_MESSAGE_BUNDLE, locale).getString("login");
            Error error = new Error(ErrorCode.OBJECT_NOT_FOUND, message, ENTITY_TYPE, null);
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseBuilder.addToErrors(error);
            writeToResponse(resp, responseBuilder.buildErrorResponse());
            return;
        }

        responseBuilder.addToResult(createToken(user));
        resp.setStatus(HttpServletResponse.SC_OK);
        writeToResponse(resp, responseBuilder.buildSuccessResponse());
    }

    /**
     * GET: /account/login
     */
    public void loginClient(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        login(req, resp, u -> u != null && (u.isInRole(Role.HANDICAPPER)));
    }

    /**
     * GET: /account/admin/login
     */
    public void loginAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        login(req, resp, u -> u != null && (u.isInRole(Role.ADMIN) || u.isInRole(Role.BOOKMAKER)));
    }


    /**
     * GET: /account/refresh/%s
     */
    public void refreshClient(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        refresh(req, resp, u -> u != null && (u.isInRole(Role.HANDICAPPER)));
    }

    /**
     * GET: /account/admin/refresh/%s
     */
    public void refreshAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        refresh(req, resp, u -> u != null && (u.isInRole(Role.ADMIN) || u.isInRole(Role.BOOKMAKER)));
    }

    /**
     * PUT: /account/roles/%d
     */
    public void setRoles(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<String> responseBuilder = createResponseBuilder(resp);
        try {
            String token = getJwtToken(req);
            if (accountService.isInRole(token, Role.ADMIN)) {
                ObjectMapper jsonMapper = new ObjectMapper();
                JavaType type = jsonMapper.getTypeFactory().constructCollectionLikeType(Set.class, Role.class);
                Set<Role> form = jsonMapper.readValue(
                        req.getReader(),
                        type
                );

                long id = getIdFromRequest(req);

                userService.setRoles(id, form);

                String message = ResourceBundle.getBundle(SUCCESS_MESSAGE_BUNDLE, locale)
                        .getString("account.set.roles");

                responseBuilder.addToResult(message);

                resp.setStatus(HttpServletResponse.SC_ACCEPTED);

                writeToResponse(resp, responseBuilder.buildSuccessResponse());
            } else {
                permissionDenied(resp, responseBuilder, locale);
            }
        } catch (JsonParseException | JsonMappingException e) {
            invalidRequest(resp, responseBuilder, locale);
        }
    }

    /**
     * PUT: /account/balance/%d
     */
    public void addMoney(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<String> responseBuilder = createResponseBuilder(resp);
        try {
            String token = getJwtToken(req);
            if (accountService.isInRole(token, Role.ADMIN)) {
                BigDecimal form = getDecimal(req);
                long id = getIdFromRequest(req);

                userService.putMoney(id, form);

                String message = ResourceBundle.getBundle(SUCCESS_MESSAGE_BUNDLE, locale)
                        .getString("account.put.money");

                responseBuilder.addToResult(message);

                resp.setStatus(HttpServletResponse.SC_ACCEPTED);

                writeToResponse(resp, responseBuilder.buildSuccessResponse());
            } else {
                permissionDenied(resp, responseBuilder, locale);
            }
        } catch (NumberFormatException e) {
            invalidRequest(resp, responseBuilder, locale);
        }

    }

    /**
     * PUT: /account/confirm/%s
     */
    public void confirmEmail(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<String> responseBuilder = createResponseBuilder(resp);

        String token = getToken(req);

        long id = accountService.getIdByConfirmToken(token);
        userService.confirmEmail(id);

        String message = ResourceBundle.getBundle(SUCCESS_MESSAGE_BUNDLE, locale)
                .getString("account.email.confirm");

        responseBuilder.addToResult(message);

        resp.setStatus(HttpServletResponse.SC_ACCEPTED);

        writeToResponse(resp, responseBuilder.buildSuccessResponse());
    }

    /**
     * GET: /account/%d
     */
    public void findById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<UserViewModel> responseBuilder = createResponseBuilder(resp);
        String token = getJwtToken(req);
        Jwt jwt = accountService.getToken(token);
        long id = getIdFromRequest(req);

        if (jwt != null && jwt.getUserId() != id && jwt.isInRole(Role.ADMIN)){
               permissionDenied(resp, responseBuilder, locale);
        } else if (id <= 0) {
            invalidId(resp, responseBuilder, locale);
        } else {
            UserDto dto = userService.find(id);
            responseBuilder.addToResult(map(dto, UserViewModel.class));

            resp.setStatus(HttpServletResponse.SC_OK);
            writeToResponse(resp, responseBuilder.buildSuccessResponse());
        }
    }

    /**
     * GET: /account?query=%s&page=%d
     */
    public void find(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<UserViewModel> responseBuilder = createResponseBuilder(resp);
        String token = getJwtToken(req);
        if (accountService.isInRole(token, Role.ADMIN)) {
            String query = req.getParameter("query");
            int page = getPageFromRequest(req);
            Pager pager = new PagerImpl(ENTITY_LIMIT, page);
            List<UserDto> users;

            if (query != null) {
                users = userService.search(query, pager);
            } else {
                users = userService.findAll(pager);
            }

            users.forEach(u -> responseBuilder.addToResult(map(u, UserViewModel.class)));
            responseBuilder.setPager(pager);

            resp.setStatus(HttpServletResponse.SC_OK);
            writeToResponse(resp, responseBuilder.buildSuccessResponse());
        } else {
            invalidRequest(resp, responseBuilder, locale);
        }
    }

    /**
     * DELETE: account/%d
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<String> responseBuilder = createResponseBuilder(resp);
        ControllerUtil.delete(req, resp, responseBuilder, accountService, locale, userService::delete);
    }
}
