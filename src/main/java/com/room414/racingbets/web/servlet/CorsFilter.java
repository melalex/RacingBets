package com.room414.racingbets.web.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Alexander Melashchenko
 * @version 1.0 06 Apr 2017
 */
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        if (resp instanceof HttpServletResponse) {
            final HttpServletResponse response = (HttpServletResponse) resp;
            setCorsHeaders(response);
        }
        chain.doFilter(req, resp);
    }

    private void setCorsHeaders(HttpServletResponse response) {
        // TODO: add my frontend server uri
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, HEADER");
        response.setHeader("Access-Control-Allow-Headers", "Authorization");
    }

    @Override
    public void destroy() {

    }
}
