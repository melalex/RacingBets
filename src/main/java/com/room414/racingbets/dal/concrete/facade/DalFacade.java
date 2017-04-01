package com.room414.racingbets.dal.concrete.facade;

import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.AbstractDalFactory;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.MainCachePool;
import com.room414.racingbets.dal.concrete.caching.redis.RedisSubscriber;
import org.apache.commons.dbcp2.BasicDataSource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.sql.SQLException;
import java.util.Properties;

/**
 * @author melalex
 * @version 1.0 08 Mar 2017
 */
public class DalFacade implements AutoCloseable {
    private static DalFacade ourInstance = new DalFacade();

    private MainCachePool pool = new MainCachePool();

    private JedisPool jedisPool;
    private BasicDataSource mysqlPool;

    private AbstractDalFactory factory;

    public static DalFacade getInstance() {
        return ourInstance;
    }

    private DalFacade() {

    }

    /**
     * @param mysqlProperties {@code Properties} with mysql connection configuration
     * @param redisProperties {@code Properties} redis connection configuration
     * @param dalFactoryProperties {@code Properties} with {@code AbstractDalFactory} configuration
     */
    public void initDal(Properties mysqlProperties, Properties redisProperties, Properties  dalFactoryProperties) {
        this.initMySqlConnectionPool(mysqlProperties);
        this.initRedisConnectionPool(redisProperties);
        this.initRedisSubscriber();
        this.initDalFactory(dalFactoryProperties);
    }

    private void initMySqlConnectionPool(Properties properties) {
        String url = properties.getProperty("jdbc.url");
        String driver = properties.getProperty("jdbc.driver");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        int maxConnections = Integer.parseInt(properties.getProperty("jdbc.connection.max.count"));

        mysqlPool = new BasicDataSource();
        mysqlPool.setDriverClassName(driver);
        mysqlPool.setUrl(url);
        mysqlPool.setUsername(username);
        mysqlPool.setPassword(password);
        mysqlPool.setMaxTotal(maxConnections);
    }

    private void initRedisConnectionPool(Properties properties) {
        String host = properties.getProperty("redis.host");
        int port = Integer.valueOf(properties.getProperty("redis.port"));
        int timeout = Integer.valueOf(properties.getProperty("redis.timeout"));
        String password = properties.getProperty("redis.password");
        int db = Integer.valueOf(properties.getProperty("redis.db"));

        jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password, db);
    }

    private void initRedisSubscriber() {
        RedisSubscriber redisSubscriber = new RedisSubscriber(pool.getCacheByNamespaceMap());
        redisSubscriber.subscribe(jedisPool.getResource());
    }

    private void initDalFactory(Properties dalFactoryProperties) {
        this.factory = AbstractDalFactoryImpl.create(mysqlPool, jedisPool, dalFactoryProperties);
    }

    public AbstractDalFactory getDalFactory() {
        return factory;
    }

    @Override
    public void close() {
        jedisPool.close();
        try {
            mysqlPool.close();
        } catch (SQLException e) {
            String message = "Exception during closing mysql connection pool";
            throw new DalException(message, e);
        }
    }
}
