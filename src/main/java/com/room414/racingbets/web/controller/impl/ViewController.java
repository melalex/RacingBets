package com.room414.racingbets.web.controller.impl;

import com.room414.racingbets.web.model.exceptions.WebException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Alexander Melashchenko
 * @version 1.0 06 Apr 2017
 */
public class ViewController {
    private static final String PATH_TO_ADMIN_PAGE = "/views/admin/index.html";
    private static final String PATH_TO_CLIENT_PAGE = "/views/client/index.html";

    public void returnAdminPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        returnPage(PATH_TO_ADMIN_PAGE, req, resp);
    }

    public void returnClientPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        returnPage(PATH_TO_CLIENT_PAGE, req, resp);
    }

    private void returnPage(String pagePath, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.setContentType("text/html");
            RequestDispatcher view = req.getRequestDispatcher(pagePath);
            view.forward(req, resp);
        } catch (ServletException e) {
            String message = "Exception during requesting admin page";
            throw new WebException(message, e);
        }
    }
}
