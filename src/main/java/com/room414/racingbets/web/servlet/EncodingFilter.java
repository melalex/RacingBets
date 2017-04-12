package com.room414.racingbets.web.servlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Apr 2017
 */
public class EncodingFilter implements Filter {
    private String encoding = "utf-8";

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        req.setCharacterEncoding(encoding);
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String encodingParam = filterConfig.getInitParameter("encoding");
        if (encodingParam != null) {
            encoding = encodingParam;
        }
    }

    @Override
    public void destroy() {

    }

}