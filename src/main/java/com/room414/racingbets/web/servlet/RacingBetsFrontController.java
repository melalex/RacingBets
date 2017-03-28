package com.room414.racingbets.web.servlet;

import com.room414.racingbets.bll.abstraction.exceptions.BllException;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.web.command.impl.CommandFactory;
import com.room414.racingbets.web.command.interfaces.Command;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.room414.racingbets.web.util.ResponseUtil.serverError;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
@WebServlet(name = "/")
public class RacingBetsFrontController extends HttpServlet {
    private static final long serialVersionUID = -5124039442023640569L;
    private static final Log LOG = LogFactory.getLog(RacingBetsFrontController.class);

    private CommandFactory postCommandFactory;
    private CommandFactory getCommandFactory;
    private CommandFactory putCommandFactory;
    private CommandFactory deleteCommandFactory;

    void setPostCommandFactory(CommandFactory postCommandFactory) {
        this.postCommandFactory = postCommandFactory;
    }

    void setGetCommandFactory(CommandFactory getCommandFactory) {
        this.getCommandFactory = getCommandFactory;
    }

    void setPutCommandFactory(CommandFactory putCommandFactory) {
        this.putCommandFactory = putCommandFactory;
    }

    void setDeleteCommandFactory(CommandFactory deleteCommandFactory) {
        this.deleteCommandFactory = deleteCommandFactory;
    }

    @Override
    public void init() throws ServletException {
        super.init();
        LifeCycle.startUp(this);
    }

    private void commandExecutor(Command com, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            com.execute(req, resp);
        } catch (BllException | DalException e) {
            LOG.error(e);
            serverError(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = postCommandFactory.create(req);
        commandExecutor(command, req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = getCommandFactory.create(req);
        commandExecutor(command, req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = putCommandFactory.create(req);
        commandExecutor(command, req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = deleteCommandFactory.create(req);
        commandExecutor(command, req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
        LifeCycle.tearDown();
    }
}
