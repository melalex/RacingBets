package com.room414.racingbets.bll.abstraction.infrastructure.jwt;

import com.room414.racingbets.dal.domain.enums.Role;

import java.util.Collection;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public interface JwtBuilder {
    JwtBuilder setExpire(long expireIn);
    JwtBuilder setUserId(long id);
    JwtBuilder setEmail(String email);
    JwtBuilder setRoles(Collection<Role> roles);
    JwtBuilder addRole(Role role);
    JwtBuilder setSignature(String signature);

    Jwt build();
}
