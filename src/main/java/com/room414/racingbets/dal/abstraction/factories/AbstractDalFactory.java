package com.room414.racingbets.dal.abstraction.factories;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public interface AbstractDalFactory {
    UnitOfWorkFactory getUnitOfWorkFactory();
    TokenStorageFactory getTokenStorageFactory();
}
