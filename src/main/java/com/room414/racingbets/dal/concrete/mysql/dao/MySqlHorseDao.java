package com.room414.racingbets.dal.concrete.mysql.dao;

import com.room414.racingbets.dal.abstraction.dao.HorseDao;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlMapHelper;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlSharedDelegate;
import com.room414.racingbets.dal.domain.entities.Horse;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
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

    private MySqlSharedDelegate<Horse> executor;

    MySqlHorseDao(Connection connection) {
        this.executor = new MySqlSharedDelegate<>(
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
        @Language("MySQL")
        final String sqlStatement =
                "INSERT INTO horse " +
                "   (name, trainer_id, owner_id, birthday, gender) " +
                "VALUES (?, ?, ?, ?, ?)";

        executor.create(
                sqlStatement,
                entity::setId,
                entity.getName(),
                entity.getTrainer().getId(),
                entity.getOwner().getId(),
                entity.getBirthday(),
                entity.getGender().getName()
        );
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
        @Language("MySQL")
        final String sqlStatement =
                "UPDATE horse " +
                "SET name = ?, trainer_id = ?, owner_id = ?, birthday = ?, gender = ? " +
                "WHERE id = ?";

        return executor.executeUpdateQuery(
                sqlStatement,
                entity.getName(),
                entity.getTrainer().getId(),
                entity.getOwner().getId(),
                entity.getBirthday(),
                entity.getGender().getName(),
                entity.getId()
        );
    }

    @Override
    public boolean delete(Long id) {
        return executor.delete(TABLE_NAME, id);
    }
}

