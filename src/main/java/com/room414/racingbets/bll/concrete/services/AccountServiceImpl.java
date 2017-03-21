package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.infrastructure.jwt.Jwt;
import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.dto.entities.UserDto;
import com.room414.racingbets.dal.abstraction.factories.TokenStorageFactory;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public class AccountServiceImpl implements AccountService {
    private TokenStorageFactory factory;

    public AccountServiceImpl(TokenStorageFactory factory) {
        this.factory = factory;
    }

    @Override
    public Jwt getToken(UserDto user) {
        return null;
    }

    @Override
    public boolean isValid(Jwt jwt) {
        return false;
    }

    @Override
    public long refreshToken(String refreshToken) {
        return 0;
    }

    @Override
    public String confirmToken(UserDto user) {
        return null;
    }

    @Override
    public boolean confirmEmail(String token) {
        return false;
    }
}
