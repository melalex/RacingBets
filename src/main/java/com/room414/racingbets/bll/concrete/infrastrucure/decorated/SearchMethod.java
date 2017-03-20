package com.room414.racingbets.bll.concrete.infrastrucure.decorated;

import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
@FunctionalInterface
public interface SearchMethod<D> {
    List<D> call(UnitOfWork unitOfWork, String searchString, Pager pager);
}
