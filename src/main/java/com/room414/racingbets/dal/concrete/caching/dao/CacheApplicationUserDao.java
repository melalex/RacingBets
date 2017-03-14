package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.dao.ApplicationUserDao;
import com.room414.racingbets.dal.abstraction.dao.CrudDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.caching.caffeine.CaffeineCache;
import com.room414.racingbets.dal.concrete.caching.caffeine.caches.ApplicationUserCache;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.enums.Role;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CacheApplicationUserDao extends CacheCrudDao<ApplicationUser> implements ApplicationUserDao {
    ApplicationUserDao dao;
    ApplicationUserCache cache;

    CacheApplicationUserDao(ApplicationUserDao dao, ApplicationUserCache cache) {
        super(dao, cache);
    }


    @Override
    public List<ApplicationUser> findByLoginPart(String loginPart, long offset, long limit) throws DalException {
        return null;
    }

    @Override
    public long findByLoginPartCount(String loginPart) throws DalException {
        return 0;
    }

    @Override
    public ApplicationUser findByLoginAndPassword(String login, String password) throws DalException {
        return null;
    }

    @Override
    public boolean confirmEmail(long id) throws DalException {
        return false;
    }

    @Override
    public void addRole(long userId, Role role) throws DalException {

    }

    @Override
    public void removeRole(long userId, Role role) throws DalException {

    }

    @Override
    public boolean tryGetMoney(long id, BigDecimal amount) throws DalException {
        return false;
    }

    @Override
    public void putMoney(long id, BigDecimal amount) throws DalException {

    }
}
