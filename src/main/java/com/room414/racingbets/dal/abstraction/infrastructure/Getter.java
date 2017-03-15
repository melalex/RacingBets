package com.room414.racingbets.dal.abstraction.infrastructure;

import com.room414.racingbets.dal.abstraction.exception.DalException;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
@FunctionalInterface
public interface Getter<T> {
    T call();
}
