package com.room414.racingbets.bll.concrete.infrastrucure;

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

    public static String updateErrorMessage(Object object) {
        return "Exception during updating entity " + object;
    }

    public static String searchErrorMessage(String searchString, int limit, int offset) {
        return String.format("Exception during searching entities with search string '%s' in [%d; %d]",
                searchString,
                offset,
                offset + limit
        );
    }

    public static String findAllErrorMessage(int limit, int offset) {
        return String.format("Exception during getting all entities in [%d; %d]",
                offset,
                offset + limit
        );
    }

    public static String deleteErrorMessage(long id) {
        return "Exception during deleting horse with id " + id;
    }

    public static String findErrorMessage(long id) {
        return "Exception during finding horse entity with id " + id;
    }
}
