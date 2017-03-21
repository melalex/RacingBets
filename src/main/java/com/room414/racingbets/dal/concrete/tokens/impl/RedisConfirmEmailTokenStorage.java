package com.room414.racingbets.dal.concrete.tokens.impl;

import com.room414.racingbets.dal.abstraction.tokens.ConfirmEmailTokenStorage;
import com.room414.racingbets.dal.concrete.tokens.delegate.TokenStorageDelegate;
import redis.clients.jedis.Jedis;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public class RedisConfirmEmailTokenStorage implements ConfirmEmailTokenStorage {
    private static final String NAME_SPACE = "confirm";

    private TokenStorageDelegate delegate;

    public RedisConfirmEmailTokenStorage(Jedis jedis, int expireIn) {
        this.delegate = new TokenStorageDelegate(jedis, NAME_SPACE, expireIn);
    }

    @Override
    public String createToken(long id) {
        return delegate.createToken(id);
    }

    @Override
    public long getIdByToken(String refreshToken) {
        return delegate.getIdByToken(refreshToken);
    }

    @Override
    public void close() {
        delegate.close();
    }
}
