package com.room414.racingbets.web.util;

import com.room414.racingbets.bll.dto.base.PersonDto;
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
    public static final int STRING_MAX_LENGTH = 45;
    public static final int STRING_MIN_LENGTH = 1;

    public static final int AUTH_STRING_MAX_LENGTH = 45;
    public static final int AUTH_STRING_MIN_LENGTH = 1;

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

    public static <T> void validateStringLength(String property,
                                                int min,
                                                int max,
                                                ResponseBuilder<T> builder,
                                                Locale locale,
                                                String name,
                                                String type) {
        if (property == null) {
            return;
        }

        if (property.length() > max || property.length() < min) {
            String message = ResourceBundle
                    .getBundle(ResponseUtil.ERROR_MESSAGE_BUNDLE, locale)
                    .getString("invalid.string.length");

            Error error = new Error(ErrorCode.INVALID_ERROR, String.format(message, min, max), type, name);
            builder.addToErrors(error);
        }
    }

    public static <T> void validateRange(long property, long min, long max, ResponseBuilder<T> builder, Locale locale, String name, String type) {
        if (property < min || property > max) {
            String message = ResourceBundle
                    .getBundle(ResponseUtil.ERROR_MESSAGE_BUNDLE, locale)
                    .getString("invalid.range");

            Error error = new Error(ErrorCode.INVALID_ERROR, String.format(message, min, max), type, name);
            builder.addToErrors(error);
        }
    }

    public static <T> void validateRange(double property, double min, double max, ResponseBuilder<T> builder, Locale locale, String name, String type) {
        if (Double.compare(property, min) < 0 || Double.compare(property, max) > 0) {
            String message = ResourceBundle
                    .getBundle(ResponseUtil.ERROR_MESSAGE_BUNDLE, locale)
                    .getString("invalid.range");

            Error error = new Error(ErrorCode.INVALID_ERROR, String.format(message, min, max), type, name);
            builder.addToErrors(error);
        }
    }

    public static <T extends PersonDto> void validatePerson(T form, ResponseBuilder<T> builder, Locale locale, String type) {
        notNull(form.getFirstName(), builder, locale, "firstName", type);
        notNull(form.getLastName(), builder, locale, "lastName", type);

        validateString(form.getFirstName(), builder, locale, "firstName", type);
        validateString(form.getLastName(), builder, locale, "lastName", type);

        validateStringLength(form.getFirstName(), STRING_MIN_LENGTH, STRING_MAX_LENGTH, builder, locale, "firstName", type);
        validateStringLength(form.getLastName(), STRING_MIN_LENGTH, STRING_MAX_LENGTH, builder, locale, "lastName", type);
    }
}
