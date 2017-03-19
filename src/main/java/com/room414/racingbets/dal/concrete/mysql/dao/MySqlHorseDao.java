package com.room414.racingbets.dal.concrete.mysql.dao;

import com.room414.racingbets.dal.abstraction.dao.HorseDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlMapHelper;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlSharedExecutor;
import com.room414.racingbets.dal.domain.entities.Horse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.*;

/**
 * Implementation of HorseDao that uses JDBC as data source.
 *
 * @see HorseDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class MySqlHorseDao implements HorseDao {
    private static final String TABLE_NAME = "horse";

    private Log log = LogFactory.getLog(MySqlHorseDao.class);

    private Connection connection;
    private MySqlSharedExecutor<Horse> executor;

    MySqlHorseDao(Connection connection) {
        this.connection = connection;
        this.executor = new MySqlSharedExecutor<>(
                connection,
                statement -> getResult(statement, MySqlMapHelper::mapHorse),
                statement -> getResultList(statement, MySqlMapHelper::mapHorse)
        );
    }

    @Override
    public List<Horse> search(String namePart, int offset, int limit) {
        @Language("MySQL")
        final String sqlStatement =
                "SELECT * FROM horse " +
                "INNER JOIN trainer AS horse_trainer " +
                "   ON horse.trainer_id = horse_trainer.id " +
                "INNER JOIN owner AS horse_owner " +
                "   ON horse.owner_id = horse_owner.id " +
                "WHERE horse.name LIKE ? " +
                "   LIMIT ? OFFSET ?";

        return executor.findByColumnPart(sqlStatement, namePart, limit, offset);
    }

    @Override
    public void create(Horse entity) {
        final String sqlStatement =
                "INSERT INTO horse " +
                "   (name, trainer_id, owner_id, birthday, gender, sire_id, dam_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getTrainer().getId());
            statement.setLong(3, entity.getOwner().getId());
            statement.setDate(4, entity.getBirthday());
            statement.setString(5, entity.getGender().getName());

            if (entity.getSir() != 0) {
                statement.setLong(6, entity.getSir());
            } else {
                statement.setNull(6, java.sql.Types.INTEGER);
            }

            if (entity.getDam() != 0) {
                statement.setLong(7, entity.getDam());
            } else {
                statement.setNull(7, java.sql.Types.INTEGER);
            }

            createEntity(statement, entity::setId);
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    entity.getName(),
                    entity.getTrainer().getId(),
                    entity.getOwner().getId(),
                    entity.getBirthday(),
                    entity.getGender().getName(),
                    entity.getSir() != 0 ? entity.getSir() : "NULL",
                    entity.getDam() != 0 ? entity.getDam() : "NULL"
            );
            log.error(message, e);
            throw new DalException(message, e);
        }
    }

    @Override
    public int searchCount(String namePart) {
        @Language("MySQL")
        final String sqlStatement = "SELECT COUNT(*) AS count FROM horse WHERE name LIKE ?";

        return executor.findByColumnPartCount(sqlStatement, namePart);
    }

    @Override
    public Horse find(Long id) {
        @Language("MySQL")
        final String sqlStatement =
                "SELECT * FROM horse " +
                "INNER JOIN trainer AS horse_trainer " +
                "   ON horse.trainer_id = horse_trainer.id " +
                "INNER JOIN owner AS horse_owner " +
                "   ON horse.owner_id = horse_owner.id " +
                "WHERE horse.id = ?";

        return executor.find(id, sqlStatement);
    }

    @Override
    public List<Horse> findAll() {
        @Language("MySQL")
        final String sqlStatement =
                "SELECT * FROM horse " +
                "INNER JOIN trainer AS horse_trainer " +
                "   ON horse.trainer_id = horse_trainer.id " +
                "INNER JOIN owner AS horse_owner " +
                "   ON horse.owner_id = horse_owner.id ";

        return executor.findAll(sqlStatement);
    }

    @Override
    public List<Horse> findAll(int offset, int limit) {
        @Language("MySQL")
        final String sqlStatement =
                "SELECT * FROM horse " +
                "INNER JOIN trainer AS horse_trainer " +
                "   ON horse.trainer_id = horse_trainer.id " +
                "INNER JOIN owner AS horse_owner " +
                "   ON horse.owner_id = horse_owner.id " +
                "LIMIT ? OFFSET ?";

        return executor.findAll(sqlStatement, limit, offset);
    }

    @Override
    public int count() {
        return executor.count(TABLE_NAME);
    }

    @Override
    public long update(Horse entity) {
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

            if (entity.getSir() != 0) {
                statement.setLong(6, entity.getSir());
            } else {
                statement.setNull(6, java.sql.Types.INTEGER);
            }

            if (entity.getDam() != 0) {
                statement.setLong(7, entity.getDam());
            } else {
                statement.setNull(7, java.sql.Types.INTEGER);
            }

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
                    entity.getSir() != 0 ? entity.getSir() : "NULL",
                    entity.getDam() != 0 ? entity.getDam() : "NULL",
                    entity.getId()
            );
            log.error(message, e);
            throw new DalException(message, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        return executor.delete(TABLE_NAME, id);
    }
}

