package com.room414.racingbets.web.servlet;

import com.room414.racingbets.bll.abstraction.factories.services.AbstractServiceFactory;
import com.room414.racingbets.bll.concrete.facade.BllFacade;
import com.room414.racingbets.dal.concrete.facade.DalFacade;
import com.room414.racingbets.web.command.impl.*;
import com.room414.racingbets.web.command.interfaces.ActionFactory;
import com.room414.racingbets.web.controller.factories.ControllerFactoryCreator;
import com.room414.racingbets.web.model.infrastructure.Route;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author Alexander Melashchenko
 * @version 1.0 28 Mar 2017
 */
class LifeCycle {
    private static final Log LOG = LogFactory.getLog(LifeCycle.class);

    private LifeCycle() {

    }

    private static Properties readProperties(String name) throws ServletException {
        try (InputStream in = LifeCycle.class.getClassLoader().getResourceAsStream(name)) {
            Properties properties = new Properties();
            properties.load(in);
            return properties;
        } catch (IOException e) {
            String message = String.format("Exception during reading properties from file '%s'", name);
            LOG.error(message, e);
            throw new ServletException(message, e);
        }
    }

    private static void initDal() throws ServletException {
        Properties mysql = readProperties("mysql.properties");
        Properties redis = readProperties("redis.properties");
        Properties dal = readProperties("dal.properties");

        DalFacade.getInstance().initDal(mysql, redis, dal);
    }

    private static void initBll() throws ServletException {
        Properties jwt = readProperties("jwt.properties");
        Properties mail = readProperties("mail.properties");
        Properties bll = readProperties("bll.properties");

        BllFacade.getInstance().init(mail, jwt, bll);
    }

    private static Map<String, ActionFactory> createActionFactoryMap() {
        Map<String, ActionFactory> result = new HashMap<>();
        AbstractServiceFactory serviceFactory = BllFacade.getInstance().getAbstractServiceFactory();
        ControllerFactoryCreator controllerFactoryCreator = new ControllerFactoryCreator(serviceFactory);

        result.put("Account", new AccountActionFactory(controllerFactoryCreator.createAccountControllerFactory()));
        result.put("Bet", new BetActionFactory(controllerFactoryCreator.createBetControllerFactory()));
        result.put("Horse", new CrudActionFactory(controllerFactoryCreator.createHorseControllerFactory()));
        result.put("Jockey", new CrudActionFactory(controllerFactoryCreator.createJockeyControllerFactory()));
        result.put("Owner", new CrudActionFactory(controllerFactoryCreator.createOwnerControllerFactory()));
        result.put("Race", new RaceActionFactory(controllerFactoryCreator.createRaceControllerFactory()));
        result.put("Racecourse", new CrudActionFactory(controllerFactoryCreator.createRacecourseControllerFactory()));
        result.put("Trainer", new CrudActionFactory(controllerFactoryCreator.createTrainerControllerFactory()));

        return result;
    }

    private static List<Route> getRoutes() {
        return null;
    }

    static void startUp(RacingBetsFrontController servlet) throws ServletException {
        initDal();
        initBll();

        Map<String, ActionFactory> actionFactoryMap = createActionFactoryMap();
        List<Route> routes = getRoutes();

        List<Route> postRoutes = routes
                .stream()
                .filter(r -> r.getMethod() == Route.Method.POST)
                .collect(Collectors.toList());

        List<Route> getRoutes = routes
                .stream()
                .filter(r -> r.getMethod() == Route.Method.GET)
                .collect(Collectors.toList());

        List<Route> putRoutes = routes
                .stream()
                .filter(r -> r.getMethod() == Route.Method.PUT)
                .collect(Collectors.toList());

        List<Route> deleteRoutes = routes
                .stream()
                .filter(r -> r.getMethod() == Route.Method.DELETE)
                .collect(Collectors.toList());

        servlet.setPostCommandFactory(new CommandFactory(postRoutes, actionFactoryMap));
        servlet.setGetCommandFactory(new CommandFactory(getRoutes, actionFactoryMap));
        servlet.setPutCommandFactory(new CommandFactory(putRoutes, actionFactoryMap));
        servlet.setDeleteCommandFactory(new CommandFactory(deleteRoutes, actionFactoryMap));
    }

    static void tearDown() {
        DalFacade.getInstance().close();
    }
}
