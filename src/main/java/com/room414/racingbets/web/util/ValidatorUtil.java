package com.room414.racingbets.web.util;

import com.room414.racingbets.web.model.builders.ResponseBuilder;
import com.room414.racingbets.web.model.enums.ErrorCode;
import com.room414.racingbets.web.model.viewmodels.Error;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Alexander Melashchenko
 * @version 1.0 24 Mar 2017
 */
public class ValidatorUtil {

    private ValidatorUtil() {

    }

    public static <T> void notNull(Object property, ResponseBuilder<T> builder, Locale locale, String name, String type) {
        if (property == null) {
            String message = ResourceBundle
                    .getBundle(ResponseUtil.ERROR_MESSAGE_BUNDLE, locale)
                    .getString("required");

            Error error = new Error(ErrorCode.NOT_NULL, message, type, name);
            builder.addToErrors(error);
        }
    }

    public static <T> void notNull(long property, ResponseBuilder<T> builder, Locale locale, String name, String type) {
        if (property == 0) {
            String message = ResourceBundle
                    .getBundle(ResponseUtil.ERROR_MESSAGE_BUNDLE, locale)
                    .getString("required");

            Error error = new Error(ErrorCode.NOT_NULL, message, type, name);
            builder.addToErrors(error);
        }
    }

    public static <T> void validateString(String property, ResponseBuilder<T> builder, Locale locale, String name, String type) {
        if (property == null) {
            return;
        }

        if (!property.matches("^[a-zA-Z0-9]*$")) {
            String message = ResourceBundle
                    .getBundle(ResponseUtil.ERROR_MESSAGE_BUNDLE, locale)
                    .getString("invalid.string");

            Error error = new Error(ErrorCode.INVALID_ERROR, message, type, name);
            builder.addToErrors(error);
        }
    }

    public static <T> void validateForeignKey(long property, ResponseBuilder<T> builder, Locale locale, String name, String type) {
        if (property < 0) {
            String message = ResourceBundle
                    .getBundle(ResponseUtil.ERROR_MESSAGE_BUNDLE, locale)
                    .getString("invalid.foreign.key");

            Error error = new Error(ErrorCode.INVALID_ERROR, message, type, name);
            builder.addToErrors(error);
        }
    }
}
