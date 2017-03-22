package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.factories.infrastructure.JwtFactory;
import com.room414.racingbets.bll.abstraction.services.AccountService;
import com.room414.racingbets.bll.concrete.infrastrucure.jwt.JwtImpl;
import com.room414.racingbets.dal.abstraction.factories.TokenStorageFactory;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.dal.resolvers.JwtFactoryResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 22 Mar 2017
 */
@ExtendWith(JwtFactoryResolver.class)
class AccountServiceImplTest {
    @Test
    void isValid_validJwt_true(JwtFactory factory) {
        AccountService accountService = new AccountServiceImpl(Mockito.mock(TokenStorageFactory.class), factory);

        JwtImpl jwt = (JwtImpl) factory
                .createBuilder()
                .setUserId(1)
                .setEmail("melalex@gmail.com")
                .addRole(Role.HANDICAPPER)
                .build();

        assert accountService.isValid(jwt);
    }

    @Test
    void isValid_invalidJwt_false(JwtFactory factory) {
        AccountService accountService = new AccountServiceImpl(Mockito.mock(TokenStorageFactory.class), factory);

        JwtImpl jwt = (JwtImpl) factory
                .createBuilder()
                .setUserId(1)
                .setEmail("melalex@gmail.com")
                .addRole(Role.HANDICAPPER)
                .build();

        Collection<Role> newRoles = new HashSet<>();
        newRoles.add(Role.HANDICAPPER);
        newRoles.add(Role.ADMIN);
        newRoles.add(Role.BOOKMAKER);

        jwt.setRoles(newRoles);

        assert !accountService.isValid(jwt);
    }
}