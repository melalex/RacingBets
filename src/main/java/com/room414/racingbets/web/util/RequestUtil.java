package com.room414.racingbets.web.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alexander Melashchenko
 * @version 1.0 25 Mar 2017
 */
public class RequestUtil {
    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER = "Bearer";

    private RequestUtil() {

    }

    /**
     * Gets JWT token from request
     */
    public static String getTokenFromRequest(HttpServletRequest req) {
        String token = null;
        String header = req.getHeader(AUTH_HEADER);

        if (header != null && header.startsWith(BEARER)) {
            token = header.substring(BEARER.length()).trim();
        }

        return token;
    }

    public static long getIdFromRequest(HttpServletRequest req) {
        try {
            return Long.parseLong(req.getRequestURI().replaceAll("[\\D]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static int getPageFromRequest(HttpServletRequest req) {
        try {
            return Integer.parseInt(req.getParameter("page"));
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}
