package com.room414.racingbets.dal.infrastructure;

import java.util.List;

/**
 * @author melalex
 * @version 1.0 09 Mar 2017
 */
@FunctionalInterface
public interface ListProducer<R> {
    List<R> invoke();
}
