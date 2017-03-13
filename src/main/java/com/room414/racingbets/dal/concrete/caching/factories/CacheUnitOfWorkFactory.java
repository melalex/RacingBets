package com.room414.racingbets.dal.concrete.caching.factories;

import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.caching.dao.CacheUnitOfWork;
import com.room414.racingbets.dal.concrete.caching.lazyload.LazyLoadUnitOfWork;
import com.room414.racingbets.dal.concrete.mysql.factories.MySqlUnitOfWorkFactory;
import redis.clients.jedis.JedisPool;

import javax.sql.DataSource;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CacheUnitOfWorkFactory implements UnitOfWorkFactory {

    private UnitOfWorkFactory unitOfWorkFactory;
    private RedisFactory redisFactory;
    // private CaffeineFactory caffeineFactory = CaffeineFactory.createFactory(redisFactory);

    public CacheUnitOfWorkFactory(DataSource mysqlConnectionPool, JedisPool redisConnectionPool) {
        this.unitOfWorkFactory = new MySqlUnitOfWorkFactory(mysqlConnectionPool);
        this.redisFactory = new RedisFactory(redisConnectionPool);
    }

    @Override
    public UnitOfWork createUnitOfWork() throws DalException {
        return new CacheUnitOfWork(new LazyLoadUnitOfWork(unitOfWorkFactory), redisFactory.create());
    }
}
