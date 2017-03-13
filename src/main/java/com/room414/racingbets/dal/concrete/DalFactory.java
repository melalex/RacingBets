package com.room414.racingbets.dal.concrete;

import com.room414.racingbets.dal.abstraction.factories.AbstractDalFactory;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.caching.factories.CacheUnitOfWorkFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;
import java.io.Closeable;
import java.io.IOException;

/**
 * @author melalex
 * @version 1.0 08 Mar 2017
 */
public class DalFactory implements AbstractDalFactory, Closeable {
    private static final String REDIS_HOST = "localhost";

    private static DalFactory ourInstance = new DalFactory();

    private JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), REDIS_HOST);
    private DataSource connectionPool;

    private UnitOfWorkFactory factory = new CacheUnitOfWorkFactory(connectionPool, jedisPool);

    public static DalFactory getInstance() {
        return ourInstance;
    }

    private DalFactory() {

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
