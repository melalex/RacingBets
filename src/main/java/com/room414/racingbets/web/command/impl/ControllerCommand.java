package com.room414.racingbets.web.command.impl;

import com.room414.racingbets.web.command.interfaces.Action;
import com.room414.racingbets.web.command.interfaces.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class ControllerCommand implements Command {
    private Action action;

    ControllerCommand(Action action) {
        this.action = action;
    }

    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        action.execute(req, resp);
    }
}
