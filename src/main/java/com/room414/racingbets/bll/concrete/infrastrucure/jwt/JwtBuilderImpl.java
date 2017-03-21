package com.room414.racingbets.bll.concrete.infrastrucure.jwt;

import com.room414.racingbets.bll.abstraction.infrastructure.jwt.Jwt;
import com.room414.racingbets.bll.abstraction.infrastructure.jwt.JwtBuilder;
import com.room414.racingbets.dal.domain.enums.Role;

import java.util.Collection;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public class JwtBuilderImpl implements JwtBuilder {
    private String algorithm;
    private String type;
    private long expire;
    private long id;
    private String email;
    private Collection<Role> roles;
    private String secret;

    public JwtBuilderImpl setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public JwtBuilderImpl setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public JwtBuilderImpl setExpire(long expireIn) {
        this.expire = expireIn;
        return this;
    }

    @Override
    public JwtBuilderImpl setUserId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public JwtBuilderImpl setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public JwtBuilderImpl setRoles(Collection<Role> roles) {
        this.roles = roles;
        return this;
    }

    @Override
    public JwtBuilderImpl addRole(Role role) {
        roles.add(role);
        return this;
    }

    public JwtBuilderImpl setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    @Override
    public Jwt build() {
        JwtImpl jwt = new JwtImpl();

        jwt.setAlgorithm(algorithm);
        jwt.setType(type);
        jwt.setExpire(expire);
        jwt.setEmail(email);
        jwt.setUserId(id);
        jwt.setRoles(roles);
        jwt.setSignature(JwtUtil.generateSignature(jwt, secret));

        return jwt;
    }
}
