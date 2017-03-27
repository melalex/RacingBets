package com.room414.racingbets.dal.concrete.mysql.base;

import com.room414.racingbets.dal.abstraction.dao.PersonDao;
import com.room414.racingbets.dal.domain.base.Person;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlSharedDelegate;

import java.sql.*;
import java.util.List;

import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 02 Mar 2017
 */
public abstract class MySqlPersonDao<T extends Person> implements PersonDao<T> {
    private MySqlSharedDelegate<T> executor;

    public MySqlPersonDao(Connection connection) {
        this.executor = new MySqlSharedDelegate<>(
                connection,
                statement -> MySqlDaoHelper.getResult(statement, this::mapResultSet),
                statement -> MySqlDaoHelper.getResultList(statement, this::mapResultSet)
        );
    }

    protected abstract String getTableName();

    protected abstract T mapResultSet(ResultSet resultSet) throws SQLException;

    @Override
    public void create(T entity) {
        String sqlStatement = String.format(
                "INSERT INTO %s (first_name, last_name, birthday) VALUES (?, ?, ?)",
                getTableName()
        );

        executor.create(
                sqlStatement,
                entity::setId,
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthday()
        );
    }

    @Override
    public List<T> search(String namePart, int offset, int limit) {
        String sqlStatement = String.format(
                "SELECT * FROM %s WHERE first_name LIKE ? OR last_name LIKE ? LIMIT ? OFFSET ?",
                getTableName()
        );

        return executor.executeFindManyQuery(
                sqlStatement,
                startsWith(namePart),
                startsWith(namePart),
                limit,
                offset
        );
    }

    @Override
    public int searchCount(String namePart) {
        String sqlStatement = String.format(
                "SELECT COUNT(*) as count FROM %s WHERE first_name LIKE ? OR last_name LIKE ?",
                getTableName()
        );

        return executor.executeCountQuery(
                sqlStatement,
                startsWith(namePart),
                startsWith(namePart)
        );
    }

    @Override
    public T find(Long id) {
        String sqlStatement = String.format(
                "SELECT * FROM %s WHERE id = ?",
                getTableName()
        );

        return executor.find(id, sqlStatement);
    }

    @Override
    public List<T> findAll() {
        String sqlStatement = String.format("SELECT * FROM %s", getTableName());

        return executor.findAll(sqlStatement);
    }

    @Override
    public List<T> findAll(int offset, int limit) {
        String sqlStatement = String.format(
                "SELECT * FROM %s LIMIT ? OFFSET ?", getTableName()
        );

        return executor.findAll(sqlStatement, limit, offset);
    }

    @Override
    public int count() {
        return executor.count(getTableName());
    }

    @Override
    public long update(T entity) {
        String sqlStatement = String.format(
                "UPDATE %s SET first_name = ?, last_name = ?, birthday = ? WHERE id = ?", getTableName()
        );

        return executor.executeUpdateQuery(
                sqlStatement,
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthday(),
                entity.getId()
        );
    }

    @Override
    public boolean delete(Long id) {
        return executor.delete(getTableName(), id);
    }
}
