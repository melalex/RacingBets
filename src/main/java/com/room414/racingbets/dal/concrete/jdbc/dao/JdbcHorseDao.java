package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.HorseDao;
import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcFindByColumnExecutor;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcMapHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.createEntity;
import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.defaultErrorMessage;

/**
 * Implementation of HorseDao that uses JDBC as data source.
 *
 * @see HorseDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcHorseDao implements HorseDao {
    private static String TABLE_NAME = "horse";

    private Connection connection;
    private JdbcFindByColumnExecutor<Horse> executor;

    JdbcHorseDao(Connection connection) {
        this.connection = connection;
        this.executor = new JdbcFindByColumnExecutor<>(connection, JdbcMapHelper::mapHorse);
    }


    @Override
    public List<Horse> findByNamePart(String namePart, long offset, long limit) throws DalException {
        final String sqlStatement =
                "SELECT * FROM horse " +
                "INNER JOIN owner " +
                "   ON horse.owner_id = owner.id " +
                "INNER JOIN trainer " +
                "   ON horse.trainer_id = trainer.id " +
                "WHERE horse.name LIKE ? " +
                "   LIMIT ? OFFSET ?";

        return executor.findByColumnPart(sqlStatement, namePart, offset, limit);
    }

    @Override
    public void create(Horse entity) throws DalException {
        final String sqlStatement =
                "INSERT INTO horse " +
                "   (name, trainer_id, owner_id, birthday, gender, sire_id, dam_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getTrainer().getId());
            statement.setLong(3, entity.getOwner().getId());
            statement.setDate(4, entity.getBirthday());
            statement.setString(5, entity.getGender().getName());
            statement.setLong(6, entity.getSir().getId());
            statement.setLong(7, entity.getDam().getId());

            createEntity(statement, entity::setId);
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    entity.getName(),
                    entity.getTrainer().getId(),
                    entity.getOwner().getId(),
                    entity.getBirthday(),
                    entity.getGender().getName(),
                    entity.getSir().getId(),
                    entity.getDam().getId()
            );
            throw new DalException(message, e);
        }
    }

    @Override
    public long findByNamePartCount(String namePart) throws DalException {
        final String sqlStatement = "SELECT COUNT(*) AS count FROM horse WHERE name LIKE ?";

        return executor.findByColumnPartCount(sqlStatement, namePart);
    }

    @Override
    public Horse find(Long id) throws DalException {
        final String sqlStatement =
                "SELECT * FROM horse " +
                "INNER JOIN owner " +
                "   ON horse.owner_id = owner.id " +
                "INNER JOIN trainer " +
                "   ON horse.trainer_id = trainer.id " +
                "WHERE horse.id = ?";

        return executor.find(id, sqlStatement);
    }

    @Override
    public List<Horse> findAll() throws DalException {
        final String sqlStatement =
                "SELECT * FROM horse " +
                "INNER JOIN owner " +
                "   ON horse.owner_id = owner.id " +
                "INNER JOIN trainer " +
                "   ON horse.trainer_id = trainer.id";

        return executor.findAll(sqlStatement);
    }

    @Override
    public List<Horse> findAll(long offset, long limit) throws DalException {
        final String sqlStatement =
                "SELECT * FROM horse " +
                "INNER JOIN owner " +
                "   ON horse.owner_id = owner.id " +
                "INNER JOIN trainer " +
                "   ON horse.trainer_id = trainer.id " +
                "LIMIT ? OFFSET ?";

        return executor.findAll(sqlStatement, limit, offset);
    }

    @Override
    public long count() throws DalException {
        return executor.count(TABLE_NAME);
    }

    @Override
    public long update(Horse entity) throws DalException {
        final String sqlStatement =
                "UPDATE horse " +
                "SET name = ?, trainer_id = ?, owner_id = ?, birthday = ?, gender = ?, sire_id = ?, dam_id = ? " +
                "WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getTrainer().getId());
            statement.setLong(3, entity.getOwner().getId());
            statement.setDate(4, entity.getBirthday());
            statement.setString(5, entity.getGender().getName());
            statement.setLong(6, entity.getSir().getId());
            statement.setLong(7, entity.getDam().getId());
            statement.setLong(8, entity.getId());

            return statement.executeUpdate();
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    entity.getName(),
                    entity.getTrainer().getId(),
                    entity.getOwner().getId(),
                    entity.getBirthday(),
                    entity.getGender().getName(),
                    entity.getSir().getId(),
                    entity.getDam().getId(),
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
