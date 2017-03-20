package com.room414.racingbets.bll.concrete.infrastrucure.decorated;

import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
@FunctionalInterface
public interface FindMethod<D>  {
   D call(UnitOfWork unitOfWork, long id);
}
