package com.room414.racingbets.dal.concrete.caching.lazyload;

import com.room414.racingbets.dal.abstraction.dao.ApplicationUserDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.enums.Role;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class LazyLoadApplicationUserDao implements ApplicationUserDao {
    private UnitOfWork unitOfWork;

    public LazyLoadApplicationUserDao(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    private ApplicationUserDao getApplicationUserDao() throws DalException {
        return unitOfWork.getApplicationUserDao();
    }

    @Override
    public void create(ApplicationUser entity) throws DalException {
        getApplicationUserDao().create(entity);
    }

    @Override
    public ApplicationUser find(Long id) throws DalException {
        return getApplicationUserDao().find(id);
    }

    @Override
    public List<ApplicationUser> findAll() throws DalException {
        return getApplicationUserDao().findAll();
    }

    @Override
    public List<ApplicationUser> findByLoginPart(String loginPart, long offset, long limit) throws DalException {
        return getApplicationUserDao().findByLoginPart(loginPart, offset, limit);
    }

    @Override
    public List<ApplicationUser> findAll(long offset, long limit) throws DalException {
        return getApplicationUserDao().findAll(offset, limit);
    }

    @Override
    public long count() throws DalException {
        return getApplicationUserDao().count();
    }

    @Override
    public long findByLoginPartCount(String loginPart) throws DalException {
        return getApplicationUserDao().findByLoginPartCount(loginPart);
    }

    @Override
    public long update(ApplicationUser entity) throws DalException {
        return getApplicationUserDao().update(entity);
    }

    @Override
    public ApplicationUser findByLoginAndPassword(String login, String password) throws DalException {
        return getApplicationUserDao().findByLoginAndPassword(login, password);
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return getApplicationUserDao().delete(id);
    }

    @Override
    public boolean confirmEmail(long id) throws DalException {
        return getApplicationUserDao().confirmEmail(id);
    }

    @Override
    public void addRole(long userId, Role role) throws DalException {
        getApplicationUserDao().addRole(userId, role);
    }

    @Override
    public void removeRole(long userId, Role role) throws DalException {
        getApplicationUserDao().removeRole(userId, role);
    }

    @Override
    public boolean tryGetMoney(long id, BigDecimal amount) throws DalException {
        return getApplicationUserDao().tryGetMoney(id, amount);
    }

    @Override
    public void putMoney(long id, BigDecimal amount) throws DalException {
        getApplicationUserDao().putMoney(id, amount);
    }
}
