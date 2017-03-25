package com.room414.racingbets.bll.concrete.infrastrucure;

import java.util.Arrays;

/**
 * @author Alexander Melashchenko
 * @version 1.0 19 Mar 2017
 */
public class ErrorMessageUtil {
    private ErrorMessageUtil() {

    }

    public static String defaultErrorMessage(String methodName, Object ... objects) {
        return String.format("Exception during executing method '%s' with argument(s) ", methodName) +
                Arrays.toString(objects);
    }
}
