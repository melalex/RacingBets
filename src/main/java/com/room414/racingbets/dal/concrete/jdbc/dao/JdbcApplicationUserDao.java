package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.ApplicationUserDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.enums.Role;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

/**
 * Implementation of ApplicationUserDao that uses JDBC as data source.
 *
 * @see ApplicationUserDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcApplicationUserDao implements ApplicationUserDao {
    private Connection connection;

    JdbcApplicationUserDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void create(ApplicationUser entity) throws DalException {

    }

    @Override
    public ApplicationUser find(Long id) {
        return null;
    }

    @Override
    public List<ApplicationUser> findAll() throws DalException {
        return null;
    }

    @Override
    public List<ApplicationUser> findAll(long offset, long limit) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public long update(ApplicationUser entity) {
        return 0;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List<ApplicationUser> findByLoginPart(String loginPart, long offset, long limit) {
        return null;
    }

    @Override
    public int findByLoginPartCount(String loginPart) {
        return 0;
    }

    @Override
    public ApplicationUser findByLogin(String login) {
        return null;
    }

    @Override
    public boolean checkPassword(long id, String password) {
        return false;
    }

    @Override
    public void confirmEmail(long id) {

    }

    @Override
    public boolean isEmailConfirmed(long id) {
        return false;
    }

    @Override
    public void addRole(long userId, Role role) {

    }

    @Override
    public void removeRole(long userId, Role role) {

    }

    @Override
    public boolean tryGetMoney(long id, BigDecimal amount) {
        return false;
    }

    @Override
    public void putMoney(long id, BigDecimal amount) {

    }
}
