package com.room414.racingbets.bll.concrete.infrastrucure.jwt;

import com.room414.racingbets.bll.abstraction.infrastructure.jwt.Jwt;
import com.room414.racingbets.bll.abstraction.infrastructure.jwt.JwtBuilder;
import com.room414.racingbets.bll.abstraction.infrastructure.jwt.JwtEncoder;
import com.room414.racingbets.dal.domain.enums.Role;

import java.util.Collection;
import java.util.HashSet;

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
    private String signature;
    private JwtEncoder encoder;

    private Collection<Role> getRoles() {
        if (roles == null) {
            roles = new HashSet<>();
        }
        return roles;
    }

    public JwtBuilderImpl setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public JwtBuilderImpl setType(String type) {
        this.type = type;
        return this;
    }

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
        getRoles().add(role);
        return this;
    }

    JwtBuilder setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    public JwtBuilderImpl setEncoder(JwtEncoder encoder) {
        this.encoder = encoder;
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
        jwt.setRoles(getRoles());
        jwt.setSignature(signature == null ? encoder.generateSignature(jwt) : signature);

        return jwt;
    }
}
