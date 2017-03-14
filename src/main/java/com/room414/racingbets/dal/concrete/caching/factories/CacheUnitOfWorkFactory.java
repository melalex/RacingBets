package com.room414.racingbets.dal.concrete.caching.factories;

import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.concrete.caching.dao.CacheUnitOfWork;
import com.room414.racingbets.dal.concrete.caching.infrastructure.lazyload.LazyLoadUnitOfWork;
import com.room414.racingbets.dal.concrete.mysql.factories.MySqlUnitOfWorkFactory;
import redis.clients.jedis.JedisPool;

import javax.sql.DataSource;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CacheUnitOfWorkFactory implements UnitOfWorkFactory {

    private UnitOfWorkFactory unitOfWorkFactory;
    private RedisUnitOfWorkFactory redisUnitOfWorkFactory;
    // private CaffeineFactory caffeineFactory = CaffeineFactory.createFactory(redisUnitOfWorkFactory);

    public CacheUnitOfWorkFactory(DataSource mysqlConnectionPool, JedisPool redisConnectionPool) {
        this.unitOfWorkFactory = new MySqlUnitOfWorkFactory(mysqlConnectionPool);
        this.redisUnitOfWorkFactory = new RedisUnitOfWorkFactory(redisConnectionPool);
    }

    @Override
    public UnitOfWork createUnitOfWork() throws DalException {
        UnitOfWork unitOfWork = new LazyLoadUnitOfWork(unitOfWorkFactory);

        return new CacheUnitOfWork(new LazyLoadUnitOfWork(unitOfWorkFactory), redisUnitOfWorkFactory.create());
    }
}
