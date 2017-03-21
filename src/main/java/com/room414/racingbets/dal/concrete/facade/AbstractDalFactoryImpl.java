package com.room414.racingbets.dal.concrete.facade;


import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.AbstractDalFactory;
import com.room414.racingbets.dal.abstraction.factories.CachingUnitOfWorkFactory;
import com.room414.racingbets.dal.abstraction.factories.TokenStorageFactory;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.caching.factories.CachedUnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.caching.factories.CaffeineCachingUnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.caching.factories.RedisUnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.caching.infrastructure.pool.MainCachePool;
import com.room414.racingbets.dal.concrete.mysql.factories.MySqlUnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.tokens.factories.RedisTokenStorageFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.JedisPool;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Alexander Melashchenko
 * @version 1.0 21 Mar 2017
 */
public class AbstractDalFactoryImpl implements AbstractDalFactory {
    private static final Log LOG = LogFactory.getLog(AbstractDalFactoryImpl.class);

    private UnitOfWorkFactory unitOfWorkFactory;
    private TokenStorageFactory tokenStorageFactory;

    private AbstractDalFactoryImpl(UnitOfWorkFactory unitOfWorkFactory, TokenStorageFactory tokenStorageFactory) {
        this.unitOfWorkFactory = unitOfWorkFactory;
        this.tokenStorageFactory = tokenStorageFactory;
    }

    public static AbstractDalFactory create(DataSource dataSource, JedisPool jedisPool, String dalFactoryProperties) {
        Properties properties = readeFactoryProperties(dalFactoryProperties);
        MainCachePool mainCachePool = new MainCachePool();

        UnitOfWorkFactory unitOfWorkFactory = new MySqlUnitOfWorkFactory(dataSource);
        RedisUnitOfWorkFactory redisUnitOfWorkFactory = new RedisUnitOfWorkFactory(jedisPool);
        CachingUnitOfWorkFactory cachingUnitOfWorkFactory = new CaffeineCachingUnitOfWorkFactory(redisUnitOfWorkFactory, mainCachePool);

        UnitOfWorkFactory cachedUnitOfWorkFactory = new CachedUnitOfWorkFactory(unitOfWorkFactory, cachingUnitOfWorkFactory);

        int refreshExpireIn = Integer.valueOf(properties.getProperty("dal.refresh.expire"));
        int confirmExpireIn = Integer.valueOf(properties.getProperty("dal.confirm.expire"));
        TokenStorageFactory tokenStorageFactory = new RedisTokenStorageFactory(jedisPool, refreshExpireIn, confirmExpireIn);

        return new AbstractDalFactoryImpl(cachedUnitOfWorkFactory, tokenStorageFactory);
    }

    private static Properties readeFactoryProperties(String filePath) {
        try {
            Properties properties = new Properties();

            InputStream is = new FileInputStream(filePath);
            properties.load(is);

            return properties;
        } catch (IOException e) {
            String message = "Exception during reading AbstractDalFactory properties";
            LOG.error(message);
            throw new DalException(message, e);
        }
    }

    @Override
    public UnitOfWorkFactory getUnitOfWorkFactory() {
        return unitOfWorkFactory;
    }

    @Override
    public TokenStorageFactory getTokenStorageFactory() {
        return tokenStorageFactory;
    }
}
