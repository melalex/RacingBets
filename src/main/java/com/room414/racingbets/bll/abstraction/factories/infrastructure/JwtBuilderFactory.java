package com.room414.racingbets.bll.abstraction.factories.infrastructure;

import com.room414.racingbets.bll.abstraction.infrastructure.jwt.JwtBuilder;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public interface JwtBuilderFactory {
    JwtBuilder create();
    String getSecret();
}
