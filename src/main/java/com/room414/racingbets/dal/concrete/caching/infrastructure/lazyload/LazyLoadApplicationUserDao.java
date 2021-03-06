package com.room414.racingbets.dal.concrete.caching.infrastructure.lazyload;

import com.room414.racingbets.dal.abstraction.dao.ApplicationUserDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.enums.Role;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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
    public List<ApplicationUser> search(String loginPart, int offset, int limit) {
        return getApplicationUserDao().search(loginPart, offset, limit);
    }

    @Override
    public List<ApplicationUser> findAll(int offset, int limit) {
        return getApplicationUserDao().findAll(offset, limit);
    }

    @Override
    public int count() {
        return getApplicationUserDao().count();
    }

    @Override
    public int searchCount(String loginPart) {
        return getApplicationUserDao().searchCount(loginPart);
    }

    @Override
    public long update(ApplicationUser entity) {
        return getApplicationUserDao().update(entity);
    }

    @Override
    public ApplicationUser findByLogin(String login) {
        return getApplicationUserDao().findByLogin(login);
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
    public void confirmEmail(long id) {
        getApplicationUserDao().confirmEmail(id);
    }

    @Override
    public void setRoles(long userId, Set<Role> roles) {
        getApplicationUserDao().setRoles(userId, roles);
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
