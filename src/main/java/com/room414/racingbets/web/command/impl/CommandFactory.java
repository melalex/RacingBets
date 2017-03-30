package com.room414.racingbets.web.command.impl;

import com.room414.racingbets.web.command.interfaces.Action;
import com.room414.racingbets.web.command.interfaces.ActionFactory;
import com.room414.racingbets.web.command.interfaces.Command;
import com.room414.racingbets.web.model.builders.ResponseBuilder;
import com.room414.racingbets.web.model.enums.ErrorCode;
import com.room414.racingbets.web.model.infrastructure.Route;
import com.room414.racingbets.web.model.viewmodels.Error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.room414.racingbets.web.util.ResponseUtil.ERROR_MESSAGE_BUNDLE;
import static com.room414.racingbets.web.util.ResponseUtil.writeToResponse;


/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class CommandFactory {
    private List<Route> routes;
    private Map<String, ActionFactory> factoryByName;

    public CommandFactory(List<Route> routes, Map<String, ActionFactory> factoryByName) {
        this.routes = routes;
        this.factoryByName = factoryByName;
    }

    private Locale getLocale(HttpServletRequest req) {
        return new Locale(req.getLocale().getLanguage());
    }

    public Command create(HttpServletRequest req) {
        String url = req.getRequestURI();
        Locale locale = getLocale(req);
        Optional<Route> optionalRoute = routes
                .stream()
                .filter(r -> url.matches(r.getPattern()))
                .findFirst();

        if (optionalRoute.isPresent()) {
            Route route = optionalRoute.get();
            Action action = factoryByName
                    .get(route.getController())
                    .create(route.getAction(), locale);

            return new ControllerCommand(action);
        } else {
            return new ControllerCommand(this::notFound);
        }
    }

    private void notFound(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResponseBuilder<Object> builder = new ResponseBuilder<>();
        String message = ResourceBundle.getBundle(ERROR_MESSAGE_BUNDLE, getLocale(req)).getString("not.found");
        Error error = new Error(ErrorCode.OBJECT_NOT_FOUND, message, null, null);
        builder.addToErrors(error);
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        writeToResponse(resp, builder.buildErrorResponse());
    }
}
