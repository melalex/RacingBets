package com.room414.racingbets.bll.concrete.infrastrucure;

import java.util.Arrays;

/**
 * @author Alexander Melashchenko
 * @version 1.0 19 Mar 2017
 */
public class ErrorMessageUtil {
    private ErrorMessageUtil() {

    }

    public static String createErrorMessage(Object object) {
        return "Exception during creating entity " + object;
    }

    static String updateErrorMessage(Object object) {
        return "Exception during updating entity " + object;
    }

    static String searchErrorMessage(String searchString, int limit, int offset) {
        return String.format("Exception during searching entities with search string '%s' in [%d; %d]",
                searchString,
                offset,
                offset + limit
        );
    }

    static String findAllErrorMessage(int limit, int offset) {
        return String.format("Exception during getting all entities in [%d; %d]",
                offset,
                offset + limit
        );
    }

    static String deleteErrorMessage(long id) {
        return "Exception during deleting horse with id " + id;
    }

    static String findErrorMessage(long id) {
        return "Exception during finding horse entity with id " + id;
    }

    public static String defaultErrorMessage(String methodName, Object ... objects) {
        return String.format("Exception during executing method '%s' with argument(s) ", methodName) +
                Arrays.toString(objects);
    }
}
