package com.room414.racingbets.web.util;

import com.room414.racingbets.web.model.builders.ResponseBuilder;
import com.room414.racingbets.web.model.enums.ErrorCode;
import com.room414.racingbets.web.model.viewmodels.ErrorViewModel;
import com.room414.racingbets.web.model.viewmodels.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Alexander Melashchenko
 * @version 1.0 24 Mar 2017
 */
public class ControllerUtil {
    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER = "Bearer";
    private static final String CONTENT_TYPE = "application/json";
    private static final String ENCODING = "UTF-8";

    public static final String ERROR_MESSAGE_BUNDLE = "ErrorMessageBundle.properties";

    private ControllerUtil() {

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

    public static <T> void writeToResponse(HttpServletResponse httpServletResponse, Response<T> response) throws IOException {
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write(response.toJson());
        writer.close();
    }

    public static <T> void permissionDenied(
            HttpServletResponse resp,
            ResponseBuilder<T> respBuilder,
            Locale locale
    ) throws IOException {
        String message = ResourceBundle.getBundle(ERROR_MESSAGE_BUNDLE, locale).getString("permission.denied");
        ErrorViewModel error = new ErrorViewModel(
                ErrorCode.INSUFFICIENT_PERMISSIONS,
                message,
                respBuilder.getType(),
                null
        );
        respBuilder.addError(error);

        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);

        writeToResponse(resp, respBuilder.buildErrorResponse());
    }

    /**
     * Sets {@code resp}s default values add create {@code ResponseBuilder} instance
     */
    public static <T> ResponseBuilder<T> createResponseBuilder(HttpServletResponse resp, Locale locale, String type) {
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(ENCODING);
        resp.setLocale(locale);

        ResponseBuilder<T> result = new ResponseBuilder<>();;
        result.setType(type);
        return result;
    }
}
