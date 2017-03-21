package com.room414.racingbets.bll.abstraction.infrastructure.jwt;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public interface JwtEncoder {
    String generateSignature(Jwt jwt);
    String encode(Jwt jwt);
}
