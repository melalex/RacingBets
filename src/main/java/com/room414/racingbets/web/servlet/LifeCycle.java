package com.room414.racingbets.web.servlet;

import com.room414.racingbets.bll.abstraction.exceptions.BllException;
import com.room414.racingbets.bll.abstraction.factories.services.AbstractServiceFactory;
import com.room414.racingbets.bll.concrete.facade.BllFacade;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.facade.DalFacade;
import com.room414.racingbets.web.command.impl.*;
import com.room414.racingbets.web.command.interfaces.ActionFactory;
import com.room414.racingbets.web.controller.factories.ControllerFactoryCreator;
import com.room414.racingbets.web.model.exceptions.WebException;
import com.room414.racingbets.web.model.infrastructure.Route;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static com.room414.racingbets.web.init.AppInitializer.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 28 Mar 2017
 */
class LifeCycle {
    private static final Log LOG = LogFactory.getLog(LifeCycle.class);

    private static final String ROUTES = "routes.xml";

    private LifeCycle() {

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

    private static List<Route> getRoutes() throws ServletException {
        try (InputStream in = LifeCycle.class.getClassLoader().getResourceAsStream(ROUTES)) {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(in);
            List<Route> result = new LinkedList<>();
            NodeList nodeList = doc.getElementsByTagName("route");

            Node node;
            Element element;

            Route.Method method;
            String pattern;
            String controller;
            String action;

            for (int i = 0; i < nodeList.getLength(); i++) {
                node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    element = (Element) node;
                    method = Route.Method.valueOf(element.getElementsByTagName("method").item(0).getTextContent());
                    pattern = element.getElementsByTagName("pattern").item(0).getTextContent();
                    controller = element.getElementsByTagName("controller").item(0).getTextContent();
                    action = element.getElementsByTagName("action").item(0).getTextContent();

                    result.add(new Route(method, pattern, controller, action));
                }
            }

            return result;
        } catch (IOException | SAXException | ParserConfigurationException e) {
            String message = String.format("Exception during reading properties from file '%s'", ROUTES);
            LOG.error(message, e);
            throw new ServletException(message, e);
        }
    }

    private static void initCommandFactories(FrontController servlet) throws ServletException {

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

    static void startUp(FrontController servlet) throws ServletException {
        try {
            initApp();
            initCommandFactories(servlet);
        } catch (WebException | BllException | DalException e) {
            String message = "Exception during application initialization";
            LOG.error(message, e);
            throw new ServletException(message, e);
        }
    }

    static void tearDown() {
        DalFacade.getInstance().close();
    }
}
