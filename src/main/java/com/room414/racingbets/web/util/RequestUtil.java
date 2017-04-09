package com.room414.racingbets.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * @author Alexander Melashchenko
 * @version 1.0 25 Mar 2017
 */
public class RequestUtil {
    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER = "Bearer";
    private static final String BASIC = "Basic";

    private RequestUtil() {

    }

    /**
     * Gets JWT token from request
     */
    public static String getJwtToken(HttpServletRequest req) {
        String token = null;
        String header = req.getHeader(AUTH_HEADER);

        if (header != null && header.startsWith(BEARER)) {
            token = header.substring(BEARER.length()).trim();
        }

        return token;
    }

    public static <T> T getObject(HttpServletRequest req, Class<T> clazz) throws IOException {
        try (BufferedReader in = req.getReader()) {
            ObjectMapper jsonMapper = new ObjectMapper();
            return jsonMapper.readValue(in, clazz);
        }
    }

    public static BigDecimal getDecimal(HttpServletRequest req) throws IOException {
        try (BufferedReader in = req.getReader()) {
            String decimal = in.readLine();

            if (decimal == null) {
                throw new NumberFormatException();
            }

            return new BigDecimal(decimal);
        }
    }

    /**
     * Gets Basic Auth from request
     *
     * @return {@code Pair} where first element is login and second is password
     */
    public static Pair<String, String> getBasicAuthFromRequest(HttpServletRequest req) {
        Pair<String, String> pair = null;
        String header = req.getHeader(AUTH_HEADER);

        if (header != null && header.startsWith(BASIC)) {
            String base64Credentials = header.substring(BASIC.length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));

            String[] values = credentials.split(":", 2);

            pair = new Pair<>(values[0], values[1]);
        }

        return pair;
    }

    public static long getIdFromRequest(HttpServletRequest req) {
        try {
            String[] values = req.getRequestURI().split("/");
            return Long.parseLong(values[values.length - 1]);
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
