package com.room414.racingbets.bll.concrete.factories.infrastructure;

import com.room414.racingbets.bll.abstraction.factories.infrastructure.JwtFactory;
import com.room414.racingbets.bll.abstraction.infrastructure.jwt.JwtBuilder;
import com.room414.racingbets.bll.abstraction.infrastructure.jwt.JwtDecoder;
import com.room414.racingbets.bll.abstraction.infrastructure.jwt.JwtEncoder;
import com.room414.racingbets.bll.concrete.infrastrucure.jwt.JwtBuilderImpl;
import com.room414.racingbets.bll.concrete.infrastrucure.jwt.JwtEncoderImpl;

import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public class JwtFactoryImpl implements JwtFactory {
    private String type;
    private String algorithm;
    private long expire;

    private JwtEncoder encoder;

    public JwtFactoryImpl(String secret, String algorithm, String type, long expire) {
        this.algorithm = algorithm;
        this.type = type;
        this.expire = expire;
        this.encoder = new JwtEncoderImpl(secret);
    }


    @Override
    public JwtBuilder createBuilder() {
        JwtBuilderImpl builder = new JwtBuilderImpl();
        long now = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC")).getTime().getTime();

        builder.setExpire(now + expire);
        builder.setAlgorithm(algorithm);
        builder.setType(type);
        builder.setEncoder(encoder);

        return builder;
    }

    @Override
    public JwtDecoder getDecoder() {
        return null;
    }

    @Override
    public JwtEncoder getEncoder() {
        return encoder;
    }
}
