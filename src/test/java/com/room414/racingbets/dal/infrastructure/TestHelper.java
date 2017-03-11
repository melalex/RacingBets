package com.room414.racingbets.dal.infrastructure;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @author melalex
 * @version 1.0 08 Mar 2017
 */
public class TestHelper {
    private TestHelper() {
    }

    public static Date sqlDateFromString(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed = format.parse(date);
        return new Date(parsed.getTime());
    }

    private static String stringFromResult(Object result) {
        if (result instanceof Collection) {
            return Arrays.toString(((Collection) result).toArray());
        } else if (result != null) {
            return result.toString();
        } else {
            return  "null";
        }
    }

    public static String defaultAssertionFailMessage(Object result, Object expectedResult) {
        return String.format("Got: %s \n Expected: %s", stringFromResult(result), stringFromResult(expectedResult));
    }
}
