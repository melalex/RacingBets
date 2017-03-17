package com.room414.racingbets.dal.concrete;

import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.AbstractDalFactory;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.caching.factories.CachedUnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.caching.factories.CaffeineCachingUnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.caching.factories.RedisUnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.MainCachePool;
import com.room414.racingbets.dal.concrete.mysql.factories.MySqlUnitOfWorkFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author melalex
 * @version 1.0 08 Mar 2017
 */
public class CachedMySqlDalFactory implements AbstractDalFactory, Closeable {
    private static final Path DB_CONFIG_FILE_PATH = Paths.get(
            System.getProperty("user.dir"),
            "database",
            "mysql",
            "main",
            "config",
            "dbConfig.properties"
    );

    private static final Path REDIS_CONFIG_FILE_PATH = Paths.get(
            System.getProperty("user.dir"),
            "database",
            "redis",
            "main",
            "config",
            "redisConfig.properties"
    );

    private static CachedMySqlDalFactory ourInstance = createDalFactory();

    private JedisPool jedisPool;
    private DataSource mysqlPool;

    private UnitOfWorkFactory factory;

    public static CachedMySqlDalFactory getInstance() {
        return ourInstance;
    }

    private CachedMySqlDalFactory() {

    }

    private static CachedMySqlDalFactory createDalFactory() {
        CachedMySqlDalFactory newInstance = new CachedMySqlDalFactory();

        newInstance.initMySqlConnectionPool();
        newInstance.initRedisConnectionPool();
        newInstance.initUnitOfWorkFactory();

        return newInstance;
    }

    // TODO: initialize data source
    private void initMySqlConnectionPool() {
        try {
            Properties properties = new Properties();

            InputStream is = new FileInputStream(DB_CONFIG_FILE_PATH.toString());
            properties.load(is);

            String url = properties.getProperty("jdbc.url");
            String driver = properties.getProperty("jdbc.driver");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");


        } catch (IOException e) {
            throw new DalException("Exception during mysql connection pool creation", e);
        }
    }

    private void initRedisConnectionPool() {
        try {
            Properties properties = new Properties();

            InputStream is = new FileInputStream(REDIS_CONFIG_FILE_PATH.toString());
            properties.load(is);

            String host = properties.getProperty("redis.host");
            int port = Integer.valueOf(properties.getProperty("redis.port"));
            int timeout = Integer.valueOf(properties.getProperty("redis.timeout"));
            String password = properties.getProperty("redis.password");
            int db = Integer.valueOf(properties.getProperty("redis.db"));

            jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password, db);
        } catch (IOException e) {
            throw new DalException("Exception during redis connection pool creation", e);
        }
    }


    private void initUnitOfWorkFactory() {
        RedisUnitOfWorkFactory redisUnitOfWorkFactory = new RedisUnitOfWorkFactory(this.jedisPool);
        UnitOfWorkFactory unitOfWorkFactory = new MySqlUnitOfWorkFactory(mysqlPool);
        MainCachePool pool = new MainCachePool();
        CaffeineCachingUnitOfWorkFactory cachingUnitOfWorkFactory = new CaffeineCachingUnitOfWorkFactory(redisUnitOfWorkFactory, pool);

        this.factory = new CachedUnitOfWorkFactory(unitOfWorkFactory, cachingUnitOfWorkFactory);
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
