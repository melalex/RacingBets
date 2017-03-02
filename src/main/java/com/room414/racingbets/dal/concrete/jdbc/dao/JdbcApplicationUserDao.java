package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.ApplicationUserDao;
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
    public boolean create(ApplicationUser entity) {

    }

    @Override
    public ApplicationUser find(Integer id) {
        return null;
    }

    @Override
    public List<ApplicationUser> findAll(int offset, int limit) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public int update(ApplicationUser entity) {

    }

    @Override
    public boolean delete(Integer id) {

    }

    @Override
    public List<ApplicationUser> findByLoginPart(String loginPart, int offset, int limit) {
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
    public boolean checkPassword(int id, String password) {
        return false;
    }

    @Override
    public void confirmEmail(int id) {

    }

    @Override
    public boolean isEmailConfirmed(int id) {
        return false;
    }

    @Override
    public void addRole(int userId, Role role) {

    }

    @Override
    public void removeRole(int userId, Role role) {

    }

    @Override
    public boolean tryGetMoney(int id, BigDecimal amount) {
        return false;
    }

    @Override
    public void putMoney(int id, BigDecimal amount) {

    }
}
