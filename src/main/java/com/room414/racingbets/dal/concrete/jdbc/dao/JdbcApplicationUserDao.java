package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.ApplicationUserDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcFindByColumnExecutor;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcMapHelper;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.enums.Role;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.createEntity;
import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.defaultErrorMessage;

/**
 * Implementation of ApplicationUserDao that uses JDBC as data source.
 *
 * @see ApplicationUserDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcApplicationUserDao implements ApplicationUserDao {
    private Connection connection;
    private JdbcFindByColumnExecutor<ApplicationUser> executor;

    JdbcApplicationUserDao(Connection connection) {
        this.connection = connection;
        this.executor = new JdbcFindByColumnExecutor<>(connection, JdbcMapHelper::mapApplicationUser);
    }

    @Override
    public void create(ApplicationUser entity) throws DalException {
        final String sqlStatement = "INSERT INTO application_user " +
                "(login, password, first_name, last_name, email, is_email_confirmed, balance) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getFirstName());
            statement.setString(4, entity.getLastName());
            statement.setString(5, entity.getEmail());
            statement.setBoolean(1, entity.isEmailConfirmed());
            statement.setBigDecimal(1, entity.getBalance());

            createEntity(statement, entity::setId);
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    entity.getLogin(),
                    entity.getPassword(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getEmail(),
                    entity.isEmailConfirmed(),
                    entity.getBalance()
            );
            throw new DalException(message, e);
        }
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
    public long update(ApplicationUser entity) throws DalException {
        final String sqlStatement = "UPDATE application_user SET " +
                "login = ?, password = ?, first_name = ?, last_name = ?, " +
                "email = ?, is_email_confirmed = ?, balance = ? " +
                "WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getFirstName());
            statement.setString(4, entity.getLastName());
            statement.setString(5, entity.getEmail());
            statement.setBoolean(6, entity.isEmailConfirmed());
            statement.setBigDecimal(7, entity.getBalance());
            statement.setLong(8, entity.getId());

            return statement.executeUpdate();
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    entity.getLogin(),
                    entity.getPassword(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getEmail(),
                    entity.isEmailConfirmed(),
                    entity.getBalance(),
                    entity.getId()
            );
            throw new DalException(message, e);
        }
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
