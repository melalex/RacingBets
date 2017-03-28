package com.room414.racingbets.dal.infrastructure.factories;

import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.caching.redis.RedisUnitOfWork;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author Alexander Melashchenko
 * @version 1.0 15 Mar 2017
 */
public class TestingRedisFactory implements AutoCloseable {
    private static final Path REDIS_CONFIG_FILE_PATH = Paths.get(
            System.getProperty("user.dir"),
            "database",
            "redis",
            "test",
            "config",
            "redis.properties"
    );
    private JedisPool jedisPool;

    private TestingRedisFactory(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public static TestingRedisFactory createInstance() {
        try {
            Properties properties = new Properties();

            InputStream is = new FileInputStream(REDIS_CONFIG_FILE_PATH.toString());
            properties.load(is);

            String host = properties.getProperty("redis.host");
            int port = Integer.valueOf(properties.getProperty("redis.port"));
            int timeout = Integer.valueOf(properties.getProperty("redis.timeout"));
            String password = properties.getProperty("redis.password");
            int db = Integer.valueOf(properties.getProperty("redis.db"));

            JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password, db);

            return new TestingRedisFactory(jedisPool);
        } catch (IOException e) {
            throw new DalException("Exception during redis connection pool creation", e);
        }
    }

    public RedisUnitOfWork create() {
        return new RedisUnitOfWork(jedisPool.getResource());
    }

    public Jedis getConnection() {
        return jedisPool.getResource();
    }

    @Override
    public void close() throws Exception {
        jedisPool.close();
    }
}