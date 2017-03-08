package com.room414.racingbets.dal.infrastructure;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
}
