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

            message = String.format(message, min, max);

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

    public static <T> void validateEmail(String property, ResponseBuilder<T> builder, Locale locale, String name, String type) {
        if (property == null) {
            return;
        }
        String pattern = "^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$";
        if (!property.matches(pattern)) {
            String message = ResourceBundle
                    .getBundle(ResponseUtil.ERROR_MESSAGE_BUNDLE, locale)
                    .getString("invalid.email");

            Error error = new Error(ErrorCode.INVALID_ERROR, message, type, name);
            builder.addToErrors(error);
        }
    }

    public static <T extends PersonDto> void validatePerson(T form, ResponseBuilder<T> builder, Locale locale, String type) {
        notNull(form.getFirstName(), builder, locale, "firstName", type);
        notNull(form.getLastName(), builder, locale, "lastName", type);

        validateString(form.getFirstName(), builder, locale, "firstName", type);
        validateString(form.getLastName(), builder, locale, "lastName", type);

        validateStringLength(form.getFirstName(), 1, 45, builder, locale, "firstName", type);
        validateStringLength(form.getLastName(), 1, 45, builder, locale, "lastName", type);
    }
}
