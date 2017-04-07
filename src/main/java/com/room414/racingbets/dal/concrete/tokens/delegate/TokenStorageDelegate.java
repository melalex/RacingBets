package com.room414.racingbets.dal.concrete.tokens.delegate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public class TokenStorageDelegate implements AutoCloseable {
    private static final int BIG_INT_LENGTH = 130;
    private static final int NUMERAL_SYSTEM = 32;

    private Jedis jedis;
    private String namespace;
    private int expireIn;

    public TokenStorageDelegate(Jedis jedis, String namespace, int expireIn) {
        this.jedis = jedis;
        this.namespace = namespace;
        this.expireIn = expireIn;
    }

    private String createToken() {
        SecureRandom random = new SecureRandom();

        return new BigInteger(BIG_INT_LENGTH, random).toString(NUMERAL_SYSTEM);
    }

    public String createToken(long id) {
        String token = createToken();
        String key = getKeyByToken(token);

        Pipeline pipeline = jedis.pipelined();
        pipeline.set(key, String.valueOf(id));
        pipeline.expire(key, expireIn);
        pipeline.sync();

        return token;
    }

    public long getIdByToken(String token) {
        String idString = jedis.get(getKeyByToken(token));
        return idString != null ? Long.valueOf(idString) : 0;
    }

    public void deleteToken(String token) {
        jedis.del(getKeyByToken(token));
    }

    private String getKeyByToken(String token) {
        return namespace + ":" + token;
    }

    @Override
    public void close() {
        jedis.close();
    }
}
