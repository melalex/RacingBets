package com.room414.racingbets.dal.concrete;

import com.room414.racingbets.dal.abstraction.factories.AbstractDalFactory;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.caching.factories.CachedUnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.caching.factories.CachingUnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.caching.factories.RedisUnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.MainCachePool;
import com.room414.racingbets.dal.concrete.mysql.factories.MySqlUnitOfWorkFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;
import java.io.Closeable;
import java.io.IOException;

/**
 * @author melalex
 * @version 1.0 08 Mar 2017
 */
// TODO: redis connection
public class CachedDalFactory implements AbstractDalFactory, Closeable {
    private static final String REDIS_HOST = "localhost";

    private static CachedDalFactory ourInstance = createDalFactory();

    private JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), REDIS_HOST);

    private UnitOfWorkFactory factory;

    public static CachedDalFactory getInstance() {
        return ourInstance;
    }

    private CachedDalFactory() {

    }

    private static CachedDalFactory createDalFactory() {
        CachedDalFactory newInstance = new CachedDalFactory();

        newInstance.jedisPool = new JedisPool(new JedisPoolConfig(), REDIS_HOST);
        // TODO: initialize data store
        DataSource connectionPool = null;

        RedisUnitOfWorkFactory redisUnitOfWorkFactory = new RedisUnitOfWorkFactory(newInstance.jedisPool);
        UnitOfWorkFactory unitOfWorkFactory = new MySqlUnitOfWorkFactory(connectionPool);
        MainCachePool pool = new MainCachePool();
        CachingUnitOfWorkFactory cachingUnitOfWorkFactory = new CachingUnitOfWorkFactory(redisUnitOfWorkFactory, pool);

        newInstance.factory = new CachedUnitOfWorkFactory(unitOfWorkFactory, cachingUnitOfWorkFactory);

        return newInstance;
    }

    @Override
    public UnitOfWorkFactory createUnitOfWorkFactory() {
        return factory;
    }

    @Override
    public void close() throws IOException {
        jedisPool.close();
    }
}
