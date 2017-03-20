package com.room414.racingbets.dal.abstraction.facade;

import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;

/**
 * @author melalex
 * @version 1.0 08 Mar 2017
 */
public interface DalFacade {
    UnitOfWorkFactory getUnitOfWorkFactory();
}
