package com.room414.racingbets.bll.abstraction.factories.infrastructure;

import com.room414.racingbets.bll.abstraction.infrastructure.jwt.JwtBuilder;
import com.room414.racingbets.bll.abstraction.infrastructure.jwt.JwtDecoder;
import com.room414.racingbets.bll.abstraction.infrastructure.jwt.JwtEncoder;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public interface JwtFactory {
    JwtBuilder createBuilder();
    JwtDecoder getDecoder();
    JwtEncoder getEncoder();
}
