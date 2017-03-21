package com.room414.racingbets.dal.concrete.tokens.delegate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.UUID;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public class TokenStorageDelegate implements AutoCloseable {
    private Jedis jedis;
    private String namespace;
    private int expireIn;

    public TokenStorageDelegate(Jedis jedis, String namespace, int expireIn) {
        this.jedis = jedis;
        this.namespace = namespace;
        this.expireIn = expireIn;
    }

    public String createToken(long id) {
        // TODO: maybe get something more powerful
        String token = UUID.randomUUID().toString();
        String key = namespace + ":" + token;

        Pipeline pipeline = jedis.pipelined();
        pipeline.set(key, String.valueOf(id));
        pipeline.expire(key, expireIn);

        return token;
    }

    public long getIdByToken(String refreshToken) {
        String idString = jedis.get(refreshToken);
        return idString != null ? Long.valueOf(idString) : 0;
    }

    @Override
    public void close() {
        jedis.close();
    }
}