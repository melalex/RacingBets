package com.room414.racingbets.dal.abstraction.factories;

import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;

/**
 * It manages the creation of UnitOfWork instances.
 *
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public interface UnitOfWorkFactory {
    UnitOfWork create() throws DalException;
}
