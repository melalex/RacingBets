package com.room414.racingbets.web.command.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
@FunctionalInterface
public interface Action {
    void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException;
}
