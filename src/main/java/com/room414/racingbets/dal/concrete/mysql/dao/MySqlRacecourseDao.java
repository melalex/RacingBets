package com.room414.racingbets.dal.concrete.mysql.dao;

import com.room414.racingbets.dal.abstraction.dao.RacecourseDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlMapHelper;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlSharedExecutor;
import com.room414.racingbets.dal.domain.entities.Racecourse;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.util.List;

import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.*;


/**
 * Implementation of RacecourseDao that uses JDBC as data source.
 *
 * @see RacecourseDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class MySqlRacecourseDao implements RacecourseDao {
    private static final String TABLE_NAME = "racecourse";

    private MySqlSharedExecutor<Racecourse> executor;

    MySqlRacecourseDao(Connection connection) {
        this.executor = new MySqlSharedExecutor<>(
                connection,
                statement -> getResult(statement, MySqlMapHelper::mapRacecourse),
                statement -> getResultList(statement, MySqlMapHelper::mapRacecourse)
        );
    }

    @Override
    public List<Racecourse> search(String namePart, int offset, int limit) {
        @Language("MySQL")
        final String sqlStatement = "SELECT * FROM racecourse WHERE racecourse.name LIKE ? LIMIT ? OFFSET ?";

        return executor.findByColumnPart(sqlStatement, namePart, limit, offset);
    }

    @Override
    public int searchCount(String namePart) {
        @Language("MySQL")
        final String sqlStatement = "SELECT COUNT(*) AS count FROM racecourse WHERE name LIKE ?";

        return executor.findByColumnPartCount(sqlStatement, namePart);
    }

    @Override
    public void create(Racecourse entity) {
        @Language("MySQL")
        final String sqlStatement =
                "INSERT INTO racecourse " +
                "   (name, latitude, longitude, contact, clerk) " +
                "VALUES (?, ?, ?, ?, ?)";

        executor.create(
                sqlStatement,
                entity::setId,
                entity.getName(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getContact(),
                entity.getClerk()
        );
    }

    @Override
    public Racecourse find(Long id) {
        @Language("MySQL")
        final String sqlStatement = "SELECT * FROM racecourse WHERE racecourse.id = ?";

        return executor.find(id, sqlStatement);
    }

    @Override
    public List<Racecourse> findAll() {
        @Language("MySQL")
        final String sqlStatement = "SELECT * FROM racecourse";

        return executor.findAll(sqlStatement);
    }

    @Override
    public List<Racecourse> findAll(int offset, int limit) {
        @Language("MySQL")
        final String sqlStatement = "SELECT * FROM racecourse LIMIT ? OFFSET ?";

        return executor.findAll(sqlStatement, limit, offset);
    }

    @Override
    public int count() throws DalException {
        return executor.count(TABLE_NAME);
    }

    @Override
    public long update(Racecourse entity) {
        @Language("MySQL")
        String sqlStatement =
                "UPDATE racecourse " +
                "SET name = ?, latitude = ?, longitude = ?, contact = ?, clerk = ? " +
                "WHERE id = ?";

        return executor.executeUpdateQuery(
                sqlStatement,
                entity.getName(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getContact(),
                entity.getClerk(),
                entity.getId()
        );
    }

    @Override
    public boolean delete(Long id) {
        return executor.delete(TABLE_NAME, id);
    }
}
