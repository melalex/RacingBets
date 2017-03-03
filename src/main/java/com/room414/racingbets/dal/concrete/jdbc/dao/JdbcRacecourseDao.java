package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.RacecourseDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcFindByColumnExecutor;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcMapHelper;
import com.room414.racingbets.dal.domain.entities.Racecourse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.defaultErrorMessage;
import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.createEntity;


/**
 * Implementation of RacecourseDao that uses JDBC as data source.
 *
 * @see RacecourseDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcRacecourseDao implements RacecourseDao {
    private static final String TABLE_NAME = "racecourse";

    private Connection connection;
    private JdbcFindByColumnExecutor<Racecourse> executor;

    JdbcRacecourseDao(Connection connection) {
        this.connection = connection;
        this.executor = new JdbcFindByColumnExecutor<>(connection, JdbcMapHelper::mapRacecourse);
    }

    @Override
    public List<Racecourse> findByNamePart(String namePart, long offset, long limit) throws DalException {
        final String sqlStatement = "SELECT * FROM racecourse " +
                "INNER JOIN country ON racecourse.country_id = country.id " +
                "WHERE racecourse.name LIKE ? LIMIT ? OFFSET ?";

        return executor.findByColumnPart(sqlStatement, namePart, offset, limit);
    }

    @Override
    public long findByNamePartCount(String namePart) throws DalException {
        final String sqlStatement = "SELECT COUNT(*) AS count FROM racecourse WHERE name LIKE ?";

        return executor.findByColumnPartCount(sqlStatement, namePart);
    }

    @Override
    // TODO: remove copy & paste
    public void create(Racecourse entity) throws DalException {
        final String sqlStatement = "INSERT INTO racecourse (name, country_id, latitude, longitude, contact, clerk) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getCountry().getId());
            statement.setDouble(3, entity.getLatitude());
            statement.setDouble(4, entity.getLongitude());
            statement.setString(5, entity.getContact());
            statement.setString(6, entity.getClerk());

            createEntity(statement, entity::setId);
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    entity.getName(),
                    entity.getCountry().getId(),
                    entity.getLatitude(),
                    entity.getLongitude(),
                    entity.getContact(),
                    entity.getClerk()
            );
            throw new DalException(message, e);
        }
    }

    @Override
    public Racecourse find(Long id) throws DalException {
        final String sqlStatement = "SELECT * FROM racecourse " +
                "INNER JOIN country ON country.id = racecourse.country_id " +
                "WHERE racecourse.id = ?";

        return executor.find(id, sqlStatement);
    }

    @Override
    public List<Racecourse> findAll() throws DalException {
        final String sqlStatement = "SELECT * FROM racecourse " +
                "INNER JOIN country ON country.id = racecourse.country_id";

        return executor.findAll(sqlStatement);
    }

    @Override
    public List<Racecourse> findAll(long offset, long limit) throws DalException {
        final String sqlStatement = "SELECT * FROM racecourse " +
                "INNER JOIN country ON country.id = racecourse.country_id " +
                "LIMIT ? OFFSET ?";

        return executor.findAll(sqlStatement, limit, offset);
    }

    @Override
    public long count() throws DalException {
        return executor.count(TABLE_NAME);
    }

    @Override
    public long update(Racecourse entity) throws DalException {
        String sqlStatement = "UPDATE racecourse " +
                "SET name = ?, country_id = ?, latitude = ?, longitude = ?, contact = ?, clerk = ? " +
                "WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getCountry().getId());
            statement.setDouble(3, entity.getLatitude());
            statement.setDouble(4, entity.getLongitude());
            statement.setString(5, entity.getContact());
            statement.setString(6, entity.getClerk());
            statement.setLong(7, entity.getId());

            return statement.executeUpdate();
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    entity.getName(),
                    entity.getCountry().getId(),
                    entity.getLatitude(),
                    entity.getLongitude(),
                    entity.getContact(),
                    entity.getClerk(),
                    entity.getId()
            );
            throw new DalException(message, e);
        }
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return executor.delete(TABLE_NAME, id);
    }
}
