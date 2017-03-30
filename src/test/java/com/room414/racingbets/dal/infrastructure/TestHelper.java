package com.room414.racingbets.dal.infrastructure;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author melalex
 * @version 1.0 08 Mar 2017
 */
// TODO: Time zones
public class TestHelper {
    private TestHelper() {
    }

    public static Date sqlDateFromString(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        java.util.Date parsed = format.parse(date);

        return new Date(parsed.getTime());
    }

    public static Timestamp sqlTimestampFromString(String timestamp) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        java.util.Date parsed = format.parse(timestamp);

        return new Timestamp(parsed.getTime());
    }

    private static String stringFromResult(Object result) {
        if (result instanceof Collection) {
            return Arrays.toString(((Collection) result).toArray());
        } else if (result != null) {
            return result.toString();
        } else {
            return "null";
        }
    }

    public static String defaultAssertionFailMessage(Object result, Object expectedResult) {
        return String.format("Got: %s \n Expected: %s", stringFromResult(result), stringFromResult(expectedResult));
    }
}
