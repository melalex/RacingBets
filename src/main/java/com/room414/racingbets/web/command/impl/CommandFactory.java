package com.room414.racingbets.web.command.impl;

import com.room414.racingbets.web.model.infrastructure.Route;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class CommandFactory {
    private List<Route> routes;

    public CommandFactory(List<Route> routes) {
        this.routes = routes;
    }

    public ControllerCommand create(String url, Locale locale) {
        Optional<Route> optionalRoute = routes
                .stream()
                .filter(r -> url.matches(r.getPattern()))
                .findFirst();

        if (optionalRoute.isPresent()) {
            Route route = optionalRoute.get();
            return null;
        } else {
            return null;
        }
    }
}
