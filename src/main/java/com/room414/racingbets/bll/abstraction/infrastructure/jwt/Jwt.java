package com.room414.racingbets.bll.abstraction.infrastructure.jwt;


import com.room414.racingbets.dal.domain.enums.Role;

import java.util.Collection;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public interface Jwt {
    // header
    String getType();
    String getAlgorithm();

    // payload
    long getExpire();
    long getUserId();
    String getEmail();
    Collection<Role> getRoles();
    boolean isInRole(Role role);

    // signature
    String getSignature();
}
