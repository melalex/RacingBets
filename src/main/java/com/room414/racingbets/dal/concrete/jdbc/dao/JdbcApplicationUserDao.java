package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.ApplicationUserDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcFindByColumnExecutor;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcMapHelper;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.enums.Role;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.*;

/**
 * Implementation of ApplicationUserDao that uses JDBC as data source.
 *
 * @see ApplicationUserDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcApplicationUserDao implements ApplicationUserDao {
    private static String TABLE_NAME = "application_user";

    private Connection connection;
    private JdbcFindByColumnExecutor<ApplicationUser> executor;

    JdbcApplicationUserDao(Connection connection) {
        this.connection = connection;
        this.executor = new JdbcFindByColumnExecutor<>(connection, JdbcMapHelper::mapApplicationUser);
    }

    private void createApplicationUser(ApplicationUser entity) throws DalException {
        final String sqlStatement =
                "INSERT INTO application_user " +
                "   (login, password, first_name, last_name, email, is_email_confirmed, balance) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getFirstName());
            statement.setString(4, entity.getLastName());
            statement.setString(5, entity.getEmail());
            statement.setBoolean(6, entity.isEmailConfirmed());
            statement.setBigDecimal(7, entity.getBalance());

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

    private void createRoles(ApplicationUser entity) throws DalException {
        final String sqlStatement =
                "INSERT INTO role " +
                "   (application_user_id, name) " +
                "VALUES (?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            for (Role role : entity.getRoles()) {
                statement.setLong(1, entity.getId());
                statement.setString(2, role.getName());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            String message = "Exception during adding role faze while creating user " + entity.toString();
            throw new DalException(message, e);
        }
    }

    @Override
    public void create(ApplicationUser entity) throws DalException {
        createApplicationUser(entity);
        createRoles(entity);
    }

    @Override
    public ApplicationUser find(Long id) throws DalException {
        //language=MySQL
        final String sqlStatement =
                "SELECT application_user.id, application_user.login, application_user.first_name, " +
                "   application_user.last_name, application_user.email, application_user.is_email_confirmed," +
                "   application_user.password, application_user.balance, role.name " +
                "FROM application_user " +
                "LEFT OUTER JOIN role " +
                "   ON application_user.id = role.application_user_id " +
                "WHERE application_user.id = ?";


        return executor.find(id, sqlStatement);
    }

    @Override
    public List<ApplicationUser> findAll() throws DalException {
        //language=MySQL
        final String sqlStatement =
                "SELECT application_user.id, application_user.login, application_user.first_name, " +
                "   application_user.last_name, application_user.email, application_user.is_email_confirmed," +
                "   application_user.password, application_user.balance, role.name " +
                "FROM application_user " +
                "LEFT OUTER JOIN role " +
                "   ON application_user.id = role.application_user_id";

        return executor.findAll(sqlStatement);
    }

    @Override
    public List<ApplicationUser> findAll(long offset, long limit) throws DalException {
        //language=MySQL
        final String sqlStatement =
                "SELECT application_user.id, application_user.login, application_user.first_name, " +
                "   application_user.last_name, application_user.email, application_user.is_email_confirmed," +
                "   application_user.password, application_user.balance, role.name " +
                "FROM (SELECT * FROM application_user LIMIT ? OFFSET ?) AS application_user " +
                "LEFT OUTER JOIN role " +
                "   ON application_user.id = role.application_user_id";

        return executor.findAll(sqlStatement, limit, offset);
    }

    @Override
    public long count() throws DalException {
        return executor.count(TABLE_NAME);
    }

    /**
     * This method didn't update user roles
     */
    @Override
    public long update(ApplicationUser entity) throws DalException {
        final String sqlStatement =
                "UPDATE application_user " +
                "SET login = ?, password = ?, first_name = ?, last_name = ?, " +
                "    email = ?, is_email_confirmed = ?, balance = ? " +
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
    public boolean delete(Long id) throws DalException {
        return executor.delete(TABLE_NAME, id);
    }

    @Override
    public List<ApplicationUser> findByLoginPart(String loginPart, long offset, long limit) throws DalException {
        //language=MySQL
        final String sqlStatement =
                "SELECT application_user.id, application_user.login, application_user.first_name, " +
                "   application_user.last_name, application_user.email, application_user.is_email_confirmed," +
                "   application_user.password, application_user.balance, role.name " +
                "FROM (" +
                "   SELECT * FROM application_user " +
                "   WHERE application_user.login LIKE ? " +
                "   LIMIT ? OFFSET ?" +
                ") AS application_user " +
                "LEFT OUTER JOIN role " +
                "   ON application_user.id = role.application_user_id";

        return executor.findByColumnPart(sqlStatement, loginPart, offset, limit);
    }

    @Override
    public long findByLoginPartCount(String loginPart) throws DalException {
        //language=MySQL
        final String sqlStatement = "SELECT COUNT(*) FROM application_user WHERE login LIKE ?";

        return executor.findByColumnPartCount(sqlStatement, loginPart);
    }

    @Override
    public ApplicationUser findByLoginAndPassword(String login, String password) throws DalException {
        final String sqlStatement =
                "SELECT application_user.id, application_user.login, application_user.first_name, " +
                "   application_user.last_name, application_user.email, application_user.is_email_confirmed," +
                "   application_user.password, application_user.balance, role.name " +
                "FROM (" +
                "   SELECT * FROM application_user " +
                "   WHERE application_user.login = ? AND application_user.password = ?" +
                ") AS application_user " +
                "LEFT OUTER JOIN role " +
                "   ON application_user.id = role.application_user_id";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, login);
            statement.setString(1, password);

            return getResult(statement, JdbcMapHelper::mapApplicationUser);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, login, password);
            throw new DalException(message, e);
        }
    }

    @Override
    public boolean confirmEmail(long id) throws DalException {
        final String sqlStatement = "UPDATE application_user SET is_email_confirmed = TRUE WHERE id = ?";

        try(Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sqlStatement) > 0;
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement);
            throw new DalException(message, e);
        }
    }

    @Override
    public void addRole(long userId, Role role) throws DalException {
        final String call = "{ CALL add_role(?, ?) }";

        try(CallableStatement statement = connection.prepareCall(call)) {
            statement.setLong(1, userId);
            statement.setString(2, role.getName());

            statement.execute();
        } catch (SQLException e) {
            String message = String.format(
                    "Exception during adding role %s to user with id  %d", role.getName(), userId
            );
            throw new DalException(message, e);
        }
    }

    @Override
    public void removeRole(long userId, Role role) throws DalException {
        final String sqlStatement = "DELETE FROM role WHERE application_user_id = ? AND name = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, userId);
            statement.setString(2, role.getName());

            statement.execute();
        } catch (SQLException e) {
            String message = String.format(
                    "Exception during removing role %s from user with id  %d", role.getName(), userId
            );
            throw new DalException(message, e);
        }
    }

    @Override
    public boolean tryGetMoney(long id, BigDecimal amount) throws DalException {
        final String sqlStatement =
                "UPDATE application_user " +
                "   SET application_user.balance = application_user.balance - ? " +
                "WHERE application_user.id = ? AND application_user.balance > ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setBigDecimal(1, amount);
            statement.setLong(2, id);
            statement.setBigDecimal(3, amount);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, amount, id, amount);
            throw new DalException(message, e);
        }
    }

    @Override
    public void putMoney(long id, BigDecimal amount) throws DalException {
        final String sqlStatement =
                "UPDATE application_user " +
                "   SET application_user.balance = application_user.balance + ? " +
                "WHERE application_user.id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setBigDecimal(1, amount);
            statement.setLong(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, amount, id);
            throw new DalException(message, e);
        }
    }
}
