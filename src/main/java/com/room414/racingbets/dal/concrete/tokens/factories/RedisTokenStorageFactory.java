package com.room414.racingbets.dal.concrete.tokens.factories;

import com.room414.racingbets.dal.abstraction.factories.TokenStorageFactory;
import com.room414.racingbets.dal.abstraction.tokens.ConfirmEmailTokenStorage;
import com.room414.racingbets.dal.abstraction.tokens.RefreshTokenStorage;
import com.room414.racingbets.dal.concrete.tokens.impl.RedisConfirmEmailTokenStorage;
import com.room414.racingbets.dal.concrete.tokens.impl.RedisRefreshTokenStorage;
import redis.clients.jedis.JedisPool;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public class RedisTokenStorageFactory implements TokenStorageFactory {
    private JedisPool pool;
    private int refreshExpireIn;
    private int confirmExpireIn;

    public RedisTokenStorageFactory(JedisPool pool, int refreshExpireIn, int confirmExpireIn) {
        this.pool = pool;
        this.refreshExpireIn = refreshExpireIn;
        this.confirmExpireIn = confirmExpireIn;

    }

    @Override
    public ConfirmEmailTokenStorage createConfirmEmailTokenStorage() {
        return new RedisConfirmEmailTokenStorage(pool.getResource(), confirmExpireIn);
    }

    @Override
    public RefreshTokenStorage createRefreshTokenStorage() {
        return new RedisRefreshTokenStorage(pool.getResource(), refreshExpireIn);
    }
}
