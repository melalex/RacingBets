package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.dao.ApplicationUserDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.enums.Role;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CacheApplicationUserDao implements ApplicationUserDao {
    private ApplicationUserDao applicationUserDao;

    public CacheApplicationUserDao(ApplicationUserDao applicationUserDao) {
        this.applicationUserDao = applicationUserDao;
    }

    @Override
    public void create(ApplicationUser entity) throws DalException {
        applicationUserDao.create(entity);
    }

    @Override
    public ApplicationUser find(Long id) throws DalException {
        return applicationUserDao.find(id);
    }

    @Override
    public List<ApplicationUser> findAll() throws DalException {
        return applicationUserDao.findAll();
    }

    @Override
    public List<ApplicationUser> findByLoginPart(String loginPart, long offset, long limit) throws DalException {
        return applicationUserDao.findByLoginPart(loginPart, offset, limit);
    }

    @Override
    public List<ApplicationUser> findAll(long offset, long limit) throws DalException {
        return applicationUserDao.findAll(offset, limit);
    }

    @Override
    public long count() throws DalException {
        return applicationUserDao.count();
    }

    @Override
    public long findByLoginPartCount(String loginPart) throws DalException {
        return applicationUserDao.findByLoginPartCount(loginPart);
    }

    @Override
    public long update(ApplicationUser entity) throws DalException {
        return applicationUserDao.update(entity);
    }

    @Override
    public ApplicationUser findByLoginAndPassword(String login, String password) throws DalException {
        return applicationUserDao.findByLoginAndPassword(login, password);
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return applicationUserDao.delete(id);
    }

    @Override
    public boolean confirmEmail(long id) throws DalException {
        return applicationUserDao.confirmEmail(id);
    }

    @Override
    public void addRole(long userId, Role role) throws DalException {
        applicationUserDao.addRole(userId, role);
    }

    @Override
    public void removeRole(long userId, Role role) throws DalException {
        applicationUserDao.removeRole(userId, role);
    }

    @Override
    public boolean tryGetMoney(long id, BigDecimal amount) throws DalException {
        return applicationUserDao.tryGetMoney(id, amount);
    }

    @Override
    public void putMoney(long id, BigDecimal amount) throws DalException {
        applicationUserDao.putMoney(id, amount);
    }
}
