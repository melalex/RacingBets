package com.room414.racingbets.web.command.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Alexander Melashchenko
 * @version 1.0 28 Mar 2017
 */
public interface Command {
    void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException;
}
