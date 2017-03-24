package com.room414.racingbets.web.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alexander Melashchenko
 * @version 1.0 24 Mar 2017
 */
public class ControllerUtil {
    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER = "Bearer";

    private ControllerUtil() {

    }

    public static String getTokenFromRequest(HttpServletRequest req) {
        String token = null;
        String header = req.getHeader(AUTH_HEADER);

        if (header != null && header.startsWith(BEARER)) {
            token = header.substring(BEARER.length()).trim();
        }

        return token;
    }
}
