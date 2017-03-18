package com.room414.racingbets.dal.concrete.mysql.dao;

import com.room414.racingbets.dal.abstraction.dao.ApplicationUserDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.exception.UserAlreadyExistsException;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlSharedExecutor;
import com.room414.racingbets.dal.domain.builders.ApplicationUserBuilder;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.enums.Role;
import org.intellij.lang.annotations.Language;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.*;

/**
 * Implementation of ApplicationUserDao that uses JDBC as data source.
 *
 * @see ApplicationUserDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class MySqlApplicationUserDao implements ApplicationUserDao {
    private static final int MYSQL_DUPLICATE = 1062;
    private static String TABLE_NAME = "application_user";

    private Connection connection;
    private MySqlSharedExecutor<ApplicationUser> executor;

    MySqlApplicationUserDao(Connection connection) {
        this.connection = connection;
        this.executor = new MySqlSharedExecutor<>(
                connection,
                statement -> getResultWithArray(statement, this::mapUsers),
                statement -> getResultListWithArray(statement, this::mapUsers)
        );
    }

    private List<ApplicationUser> mapUsers(ResultSet resultSet) throws SQLException {
        Map<Long, ApplicationUserBuilder> builderById = new HashMap<>();

        final String idColumnName = "application_user.id";
        final String loginColumnName = "application_user.login";
        final String firstNameColumnName = "application_user.first_name";
        final String lastNameColumnName = "application_user.last_name";
        final String emailColumnName = "application_user.email";
        final String isEmailConfirmedColumnName = "application_user.is_email_confirmed";
        final String passwordColumnName = "application_user.password";
        final String balanceColumnName = "application_user.balance";
        final String roleNameColumnName = "role.name";

        ApplicationUserBuilder builder;
        long id;

        while (resultSet.next()) {
            id = resultSet.getLong(idColumnName);
            builder = builderById.get(id);
            if (builder == null) {
                builder = ApplicationUser.builder()
                        .setId(id)
                        .setLogin(resultSet.getString(loginColumnName))
                        .setFirstName(resultSet.getString(firstNameColumnName))
                        .setLastName(resultSet.getString(lastNameColumnName))
                        .setEmail(resultSet.getString(emailColumnName))
                        .setEmailConfirmed(resultSet.getBoolean(isEmailConfirmedColumnName))
                        .setPassword(resultSet.getString(passwordColumnName))
                        .setBalance(resultSet.getBigDecimal(balanceColumnName));

                builderById.put(id, builder);
            }
            builder.addRole(resultSet.getString(roleNameColumnName));
        }

        return builderById
                .values()
                .stream()
                .map(ApplicationUserBuilder::build)
                .collect(Collectors.toList());
    }

    private void createApplicationUser(ApplicationUser entity) {
        final String sqlStatement =
                "INSERT INTO application_user " +
                "   (login, password, first_name, last_name, email, is_email_confirmed, balance) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getFirstName());
            statement.setString(4, entity.getLastName());
            statement.setString(5, entity.getEmail());
            statement.setBoolean(6, entity.isEmailConfirmed());
            statement.setBigDecimal(7, entity.getBalance());

            createEntity(statement, entity::setId);
        } catch (SQLException e) {
            if(e.getErrorCode() == MYSQL_DUPLICATE){
                String message = String.format(
                        "User with login %s or email %s already exists", entity.getLogin(), entity.getEmail()
                );
                boolean emailExist = e.getMessage().contains("email_UNIQUE");
                boolean loginExist = e.getMessage().contains("login_UNIQUE");
                throw new UserAlreadyExistsException(message, e, emailExist, loginExist);
            }

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

    private void createRoles(ApplicationUser entity) {
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
    public void create(ApplicationUser entity) {
        createApplicationUser(entity);
        createRoles(entity);
    }

    @Override
    public ApplicationUser find(Long id) {
        @Language("MySQL")
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
    public List<ApplicationUser> findAll() {
        @Language("MySQL")
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
    public List<ApplicationUser> findAll(long offset, long limit) {
        @Language("MySQL")
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
    public long count() {
        return executor.count(TABLE_NAME);
    }

    /**
     * This method didn't update user roles
     */
    @Override
    public long update(ApplicationUser entity) {
        @Language("MySQL")
        final String sqlStatement =
                "UPDATE application_user " +
                "SET login = ?, password = ?, first_name = ?, last_name = ?, " +
                "    email = ?, is_email_confirmed = ?, balance = ? " +
                "WHERE id = ?";

        return executor.executeUpdateQuery(
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
    }

    @Override
    public boolean delete(Long id) {
        return executor.delete(TABLE_NAME, id);
    }

    @Override
    public List<ApplicationUser> findByLoginPart(String loginPart, long offset, long limit) {
        @Language("MySQL")
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

        return executor.findByColumnPart(sqlStatement, loginPart, limit, offset);
    }

    @Override
    public long findByLoginPartCount(String loginPart) {
        @Language("MySQL")
        final String sqlStatement = "SELECT COUNT(*) AS count FROM application_user WHERE login LIKE ?";

        return executor.findByColumnPartCount(sqlStatement, loginPart);
    }

    @Override
    public ApplicationUser findByLoginAndPassword(String login, String password) {
        @Language("MySQL")
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

        return executor.executeFindOneQuery(sqlStatement, login, password);
    }

    @Override
    public List<ApplicationUser> findByLoginAndEmail(String login, String email) {
        @Language("MySQL")
        final String sqlStatement =
                "SELECT application_user.id, application_user.login, application_user.first_name, " +
                "   application_user.last_name, application_user.email, application_user.is_email_confirmed," +
                "   application_user.password, application_user.balance, role.name " +
                "FROM (" +
                "   SELECT * FROM application_user " +
                "   WHERE application_user.login = ? OR application_user.email = ?" +
                ") AS application_user " +
                "LEFT OUTER JOIN role " +
                "   ON application_user.id = role.application_user_id";

        return executor.executeFindManyQuery(sqlStatement, login, email);
    }

    @Override
    public void confirmEmail(long id) {
        @Language("MySQL")
        final String sqlStatement = "UPDATE application_user SET is_email_confirmed = TRUE WHERE id = ?";

        executor.executeSimpleQuery(sqlStatement, id);
    }

    @Override
    public void addRole(long userId, Role role) {
        @Language("MySQL")
        final String call = "{ CALL add_role(?, ?) }";

        executor.executeSimpleQuery(call, userId, role.getName());
    }

    @Override
    public void removeRole(long userId, Role role) {
        @Language("MySQL")
        final String sqlStatement = "DELETE FROM role WHERE application_user_id = ? AND name = ?";

        executor.executeUpdateQuery(sqlStatement, userId, role.getName());
    }

    @Override
    public boolean tryGetMoney(long id, BigDecimal amount) {
        @Language("MySQL")
        final String sqlStatement =
                "UPDATE application_user " +
                "   SET application_user.balance = application_user.balance - ? " +
                "WHERE application_user.id = ? AND application_user.balance > ?";

        return executor.executeUpdateQuery(sqlStatement, amount, id, amount) > 0;
    }

    @Override
    public void putMoney(long id, BigDecimal amount) {
        @Language("MySQL")
        final String sqlStatement =
                "UPDATE application_user " +
                "   SET application_user.balance = application_user.balance + ? " +
                "WHERE application_user.id = ?";

        executor.executeSimpleQuery(sqlStatement, amount, id);
    }
}
