package com.room414.racingbets.bll.abstraction.infrastructure.jwt;

import com.room414.racingbets.bll.abstraction.factories.infrastructure.JwtFactory;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.dal.resolvers.JwtFactoryResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.room414.racingbets.dal.infrastructure.TestHelper.defaultAssertionFailMessage;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 22 Mar 2017
 */
@ExtendWith(JwtFactoryResolver.class)
class JwtTest {

    @Test
    void decodeAndEncode(JwtFactory factory) {
        Jwt jwt = factory
                .createBuilder()
                .setUserId(1)
                .setEmail("melalex@gmail.com")
                .addRole(Role.HANDICAPPER)
                .addRole(Role.ADMIN)
                .addRole(Role.BOOKMAKER)
                .build();

        String token = factory.getEncoder().encode(jwt);
        Jwt decoded = factory.getDecoder().decode(token);
        assert jwt.equals(decoded) : defaultAssertionFailMessage(decoded, decoded);
    }
}