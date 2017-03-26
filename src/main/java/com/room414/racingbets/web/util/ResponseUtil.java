package com.room414.racingbets.web.util;

import com.room414.racingbets.web.model.builders.ResponseBuilder;
import com.room414.racingbets.web.model.enums.ErrorCode;
import com.room414.racingbets.web.model.viewmodels.Error;
import com.room414.racingbets.web.model.viewmodels.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Alexander Melashchenko
 * @version 1.0 25 Mar 2017
 */
public class ResponseUtil {
    private static final String CONTENT_TYPE = "application/json";
    private static final String ENCODING = "UTF-8";

    public static final String ERROR_MESSAGE_BUNDLE = "ErrorMessageBundle.properties";
    public static final int UNPROCESSABLE_ENTITY = 422;


    private ResponseUtil() {

    }

    public static <T> void writeToResponse(HttpServletResponse httpServletResponse,
                                           Response<T> response) throws IOException {
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write(response.toJson());
        writer.close();
    }

    public static <T> void invalidRequest(HttpServletResponse resp,
                                          ResponseBuilder<T> builder,
                                          Locale locale) throws IOException {
        String messageForResponse = ResourceBundle
                .getBundle(ERROR_MESSAGE_BUNDLE, locale)
                .getString("invalid.request");

        invalidRequest(resp, builder, messageForResponse);
    }

    public static <T> void invalidRequest(HttpServletResponse resp,
                                          ResponseBuilder<T> builder,
                                          String message) throws IOException {

        Error error = new Error(ErrorCode.INVALID_ARGUMENT, message, builder.getType(), null);
        builder.addToErrors(error);
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        writeToResponse(resp, builder.buildErrorResponse());
    }

    public static <T> void permissionDenied(HttpServletResponse resp,
                                            ResponseBuilder<T> builder,
                                            Locale locale) throws IOException {
        String message = ResourceBundle.getBundle(ERROR_MESSAGE_BUNDLE, locale).getString("permission.denied");
        Error error = new Error(
                ErrorCode.INSUFFICIENT_PERMISSIONS,
                message,
                builder.getType(),
                null
        );
        builder.addToErrors(error);

        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);

        writeToResponse(resp, builder.buildErrorResponse());
    }

    /**
     * Sets {@code resp}s default values add create {@code ResponseBuilder} instance
     */
    public static <T> ResponseBuilder<T> createResponseBuilder(HttpServletResponse resp, Locale locale, String type) {
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding(ENCODING);
        resp.setLocale(locale);

        ResponseBuilder<T> result = new ResponseBuilder<>();

        result.setType(type);
        return result;
    }
}
