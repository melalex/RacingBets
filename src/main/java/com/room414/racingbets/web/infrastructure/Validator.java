package com.room414.racingbets.web.infrastructure;

import com.room414.racingbets.web.model.builders.ResponseBuilder;

/**
 * @author Alexander Melashchenko
 * @version 1.0 25 Mar 2017
 */
@FunctionalInterface
public interface Validator<F, D>  {
   void validate(F value, ResponseBuilder<D> builder);
}
