package com.room414.racingbets.web.util;

/**
 * @author Alexander Melashchenko
 * @version 1.0 25 Mar 2017
 */
public class ExceptionMessageUtil {
    private ExceptionMessageUtil() {

    }

    public static String createErrorMessage(String type) {
        return String.format("Exception during %s creating", type);
    }

    public static String updateErrorMessage(String type) {
        return String.format("Exception during %s updating", type);
    }

    public static String getErrorMessage(String type) {
        return String.format("Exception during %s getting", type);
    }

    public static String deleteErrorMessage(String type) {
        return String.format("Exception during %s deleting", type);
    }
}
