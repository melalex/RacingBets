package com.room414.racingbets.web.controller.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public interface CrudController {
    void create(HttpServletRequest req, HttpServletResponse resp) throws IOException;
    void update(HttpServletRequest req, HttpServletResponse resp) throws IOException;
    void find(HttpServletRequest req, HttpServletResponse resp) throws IOException;
    void search(HttpServletRequest req, HttpServletResponse resp) throws IOException;
    void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException;
}
