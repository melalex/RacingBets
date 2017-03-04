package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.RaceDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.RaceStatus;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

/**
 * Implementation of RaceDao that uses JDBC as data source.
 *
 * @see RaceDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
// TODO: ordered
public class JdbcRaceDao implements RaceDao {
    Connection connection;

    JdbcRaceDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void create(Race entity) throws DalException {

    }

    @Override
    public Race find(Integer id) {
        return null;
    }

    @Override
    public List<Race> findAll() throws DalException {
        return null;
    }

    @Override
    public List<Race> findAll(long offset, long limit) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public long update(Race entity) {
        return 0;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }


    @Override
    public List<Race> findScheduledByRacecourseId(long id, long offset, long limit) {
        return null;
    }

    @Override
    public long findScheduledByRacecourseIdCount(long id) {
        return 0;
    }

    @Override
    public List<Race> findFinishedByRacecourseId(long id, long offset, long limit) {
        return null;
    }

    @Override
    public long findFinishedByRacecourseIdCount(long id) {
        return 0;
    }

    @Override
    public List<Race> findScheduledInTimestampDiapason(Timestamp begin, Timestamp end, long offset, long limit) {
        return null;
    }

    @Override
    public long findScheduledInTimestampDiapasonCount(Timestamp begin, Timestamp end) {
        return 0;
    }

    @Override
    public List<Race> findFinishedInTimestampDiapason(Timestamp begin, Timestamp end, long offset, long limit) {
        return null;
    }

    @Override
    public long findFinishedInTimestampDiapasonCount(Timestamp begin, Timestamp end) {
        return 0;
    }

    @Override
    public List<Race> findScheduledInTimestampDiapasonOnRacecourse(long racecourseId, Timestamp begin, Timestamp end, long offset, long limit) {
        return null;
    }

    @Override
    public long findScheduledInTimestampDiapasonOnRacecourseCount(long racecourseId, Timestamp begin, Timestamp end) {
        return 0;
    }

    @Override
    public List<Race> findFinishedInTimestampDiapasonOnRacecourse(long racecourseId, Timestamp begin, Timestamp end, long offset, long limit) {
        return null;
    }

    @Override
    public long findFinishedInTimestampDiapasonOnRacecourseCount(long racecourseId, Timestamp begin, Timestamp end) {
        return 0;
    }

    @Override
    public List<Race> findByNamePart(String namePart, long offset, long limit) {
        return null;
    }

    @Override
    public int findByNamePartCount(String namePart) {
        return 0;
    }

    @Override
    public void updateStatus(long id, RaceStatus status) {

    }
}
