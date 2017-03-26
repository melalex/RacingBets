package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.factories.infrastructure.JwtFactory;
import com.room414.racingbets.bll.abstraction.infrastructure.jwt.Jwt;
import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.dto.entities.UserDto;
import com.room414.racingbets.dal.abstraction.factories.TokenStorageFactory;
import com.room414.racingbets.dal.abstraction.tokens.ConfirmEmailTokenStorage;
import com.room414.racingbets.dal.abstraction.tokens.RefreshTokenStorage;
import com.room414.racingbets.dal.domain.enums.Role;

import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public class AccountServiceImpl implements AccountService {
    private TokenStorageFactory tokenStorageFactory;
    private JwtFactory jwtFactory;

    public AccountServiceImpl(TokenStorageFactory tokenStorageFactory, JwtFactory jwtFactory) {
        this.tokenStorageFactory = tokenStorageFactory;
        this.jwtFactory = jwtFactory;
    }

    @Override
    public Jwt getToken(UserDto user) {
        return jwtFactory
                .createBuilder()
                .setUserId(user.getId())
                .setEmail(user.getEmail())
                .setRoles(user.getRoles())
                .build();
    }

    @Override
    public String getTokenString(UserDto user) {
        return getToken(getToken(user));
    }

    @Override
    public String getToken(Jwt jwt) {
        return jwtFactory.getEncoder().encode(jwt);
    }

    @Override
    public Jwt getToken(String token) {
        return jwtFactory.getDecoder().decode(token);
    }

    @Override
    public boolean isValid(Jwt jwt) {
        String expectedSignature = jwtFactory.getEncoder().generateSignature(jwt);
        long now = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC")).getTime().getTime();
        return expectedSignature.equals(jwt.getSignature())
                && jwt.getExpire() > now;
    }

    @Override
    public boolean isInRole(String token, Role role) {
        Jwt jwt = jwtFactory.getDecoder().decode(token);
        return isValid(jwt) && jwt.isInRole(role);
    }

    @Override
    public String getRefreshToken(long id) {
        try (RefreshTokenStorage refreshTokenStorage = tokenStorageFactory.createRefreshTokenStorage()) {
            return refreshTokenStorage.createToken(id);
        }
    }

    @Override
    public long getIdByRefreshToken(String refreshToken) {
        try (RefreshTokenStorage refreshTokenStorage = tokenStorageFactory.createRefreshTokenStorage()) {
            return refreshTokenStorage.getIdByToken(refreshToken);
        }
    }

    @Override
    public void deleteRefreshToken(String refreshToken) {
        try (RefreshTokenStorage refreshTokenStorage = tokenStorageFactory.createRefreshTokenStorage()) {
            refreshTokenStorage.deleteToken(refreshToken);
        }
    }

    @Override
    public String getConfirmToken(long userId) {
        try (ConfirmEmailTokenStorage confirmEmailTokenStorage = tokenStorageFactory.createConfirmEmailTokenStorage()) {
            return confirmEmailTokenStorage.createToken(userId);
        }
    }

    @Override
    public long getIdByConfirmToken(String confirmToken) {
        try (ConfirmEmailTokenStorage confirmEmailTokenStorage = tokenStorageFactory.createConfirmEmailTokenStorage()) {
            return confirmEmailTokenStorage.getIdByToken(confirmToken);
        }
    }
}
