package com.room414.racingbets.bll.concrete.factories.infrastructure;

import com.room414.racingbets.bll.abstraction.factories.infrastructure.JwtBuilderFactory;
import com.room414.racingbets.bll.abstraction.infrastructure.jwt.JwtBuilder;
import com.room414.racingbets.bll.concrete.infrastrucure.jwt.JwtBuilderImpl;

import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public class JwtBuilderFactoryImpl implements JwtBuilderFactory {
    private String secret;
    private String type;
    private String algorithm;
    private long expire;

    public JwtBuilderFactoryImpl(String secret, String algorithm, String type, long expire) {
        this.secret = secret;
        this.algorithm = algorithm;
        this.type = type;
        this.expire = expire;
    }

    public String getSecret() {
        return secret;
    }

    @Override
    public JwtBuilder create() {
        JwtBuilderImpl builder = new JwtBuilderImpl();
        long now = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC")).getTime().getTime();

        builder.setExpire(now + expire);
        builder.setAlgorithm(algorithm);
        builder.setType(type);
        builder.setSecret(secret);

        return builder;
    }
}
