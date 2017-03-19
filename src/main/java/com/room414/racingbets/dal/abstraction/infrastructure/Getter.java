package com.room414.racingbets.dal.abstraction.infrastructure;


/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
@FunctionalInterface
public interface Getter<T> {
    T call();
}
