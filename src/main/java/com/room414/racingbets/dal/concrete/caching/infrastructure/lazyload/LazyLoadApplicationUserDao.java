package com.room414.racingbets.dal.concrete.caching.infrastructure.lazyload;

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
    public void create(ApplicationUser entity) {
        getApplicationUserDao().create(entity);
    }

    @Override
    public ApplicationUser find(Long id) {
        return getApplicationUserDao().find(id);
    }

    @Override
    public List<ApplicationUser> findAll() {
        return getApplicationUserDao().findAll();
    }

    @Override
    public List<ApplicationUser> findByLoginPart(String loginPart, long offset, long limit) {
        return getApplicationUserDao().findByLoginPart(loginPart, offset, limit);
    }

    @Override
    public List<ApplicationUser> findAll(long offset, long limit) {
        return getApplicationUserDao().findAll(offset, limit);
    }

    @Override
    public long count() {
        return getApplicationUserDao().count();
    }

    @Override
    public long findByLoginPartCount(String loginPart) {
        return getApplicationUserDao().findByLoginPartCount(loginPart);
    }

    @Override
    public long update(ApplicationUser entity) {
        return getApplicationUserDao().update(entity);
    }

    @Override
    public ApplicationUser findByLoginAndPassword(String login, String password) {
        return getApplicationUserDao().findByLoginAndPassword(login, password);
    }

    @Override
    public List<ApplicationUser> findByLoginAndEmail(String login, String email) {
        return getApplicationUserDao().findByLoginAndEmail(login, email);
    }

    @Override
    public boolean delete(Long id) {
        return getApplicationUserDao().delete(id);
    }

    @Override
    public boolean confirmEmail(long id) {
        return getApplicationUserDao().confirmEmail(id);
    }

    @Override
    public void addRole(long userId, Role role) {
        getApplicationUserDao().addRole(userId, role);
    }

    @Override
    public void removeRole(long userId, Role role) {
        getApplicationUserDao().removeRole(userId, role);
    }

    @Override
    public boolean tryGetMoney(long id, BigDecimal amount) {
        return getApplicationUserDao().tryGetMoney(id, amount);
    }

    @Override
    public void putMoney(long id, BigDecimal amount) {
        getApplicationUserDao().putMoney(id, amount);
    }
}
