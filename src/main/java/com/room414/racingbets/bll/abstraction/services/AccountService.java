package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.abstraction.infrastructure.jwt.Jwt;
import com.room414.racingbets.bll.dto.entities.UserDto;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public interface AccountService {
    Jwt getToken(UserDto user);
    boolean isValid(Jwt jwt);
    long refreshToken(String refreshToken);

    String confirmToken(UserDto user);
    boolean confirmEmail(String token);
}
