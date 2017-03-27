package com.room414.racingbets.web.command.impl;

import com.room414.racingbets.web.command.interfaces.Action;
import com.room414.racingbets.web.command.interfaces.ActionFactory;
import com.room414.racingbets.web.command.interfaces.Command;
import com.room414.racingbets.web.model.infrastructure.Route;
import com.room414.racingbets.web.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


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

    public Command create(HttpServletRequest req) {
        String url = req.getRequestURI();
        Locale locale = req.getLocale();
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
            return new ControllerCommand(ResponseUtil::notFound);
        }
    }
}
