package com.room414.racingbets.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class ControllerCommand {
    private Action action;

    ControllerCommand(Action action) {
        this.action = action;
    }

    void execute(HttpServletRequest req, HttpServletResponse resp) {
        action.execute(req, resp);
    }
}
