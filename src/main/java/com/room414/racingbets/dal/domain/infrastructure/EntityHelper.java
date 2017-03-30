package com.room414.racingbets.dal.domain.infrastructure;

import java.sql.Date;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

/**
 * @author melalex
 * @version 1.0 12 Mar 2017
 */
public class EntityHelper {

    private EntityHelper() {

    }

    public static <K, E> boolean compareMaps(Map<K, E> map1, Map<K, E> map2, Comparator<E> comparator) {
        Set<K> keys = map1.keySet();

        if (!keys.equals(map2.keySet())) {
            return false;
        }

        for (K key : keys) {
            if (comparator.compare(map1.get(key), map2.get(key)) != 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean compareDates(Date date1, Date date2) {
        Calendar day1 = Calendar.getInstance();
        Calendar day2 = Calendar.getInstance();
        day1.setTime(date1);
        day2.setTime(date2);

        return day1.get(Calendar.YEAR) == day2.get(Calendar.YEAR) &&
               day1.get(Calendar.DAY_OF_YEAR) == day2.get(Calendar.DAY_OF_YEAR);
    }

    public static int dateHash(Date date) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        int result = day.get(Calendar.YEAR);
        return 31 * result + day.get(Calendar.DAY_OF_YEAR);
    }
}
