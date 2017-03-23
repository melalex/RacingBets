package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.abstraction.infrastructure.jwt.Jwt;
import com.room414.racingbets.bll.dto.entities.UserDto;
import com.room414.racingbets.dal.domain.enums.Role;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public interface AccountService {
    Jwt getToken(UserDto user);
    String getToken(Jwt jwt);
    Jwt getToken(String token);

    boolean isValid(Jwt jwt);
    boolean isInRole(String token, Role role);

    String getRefreshToken(long userId);
    long getIdByRefreshToken(String refreshToken);

    String getConfirmToken(long userId);
    long getIdByConfirmToken(String confirmToken);
}
