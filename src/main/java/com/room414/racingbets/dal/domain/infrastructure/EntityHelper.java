package com.room414.racingbets.dal.domain.infrastructure;

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
}
