package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.RaceDao;
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
public class JdbcRaceDao implements RaceDao {
    Connection connection;

    JdbcRaceDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean create(Race entity) {

    }

    @Override
    public Race find(Integer id) {
        return null;
    }

    @Override
    public List<Race> findAll(int offset, int limit) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public int update(Race entity) {

    }

    @Override
    public boolean delete(Integer id) {

    }

    @Override
    public List<Race> findScheduledByRacecourseId(int id, int offset, int limit) {
        return null;
    }

    @Override
    public List<Race> findScheduledByRacecourseIdCount(int id) {
        return null;
    }

    @Override
    public List<Race> findFinishedByRacecourseId(int id, int offset, int limit) {
        return null;
    }

    @Override
    public List<Race> findFinishedByRacecourseIdCount(int id) {
        return null;
    }

    @Override
    public List<Race> findScheduledInTimestampDiapason(Timestamp begin, Timestamp end, int offset, int limit) {
        return null;
    }

    @Override
    public List<Race> findScheduledInTimestampDiapasonCount(Timestamp begin, Timestamp end) {
        return null;
    }

    @Override
    public List<Race> findFinishedInTimestampDiapason(Timestamp begin, Timestamp end, int offset, int limit) {
        return null;
    }

    @Override
    public List<Race> findFinishedInTimestampDiapasonCount(Timestamp begin, Timestamp end) {
        return null;
    }

    @Override
    public List<Race> findScheduledInTimestampDiapasonOnRacecourse(int racecourseId, Timestamp begin, Timestamp end, int offset, int limit) {
        return null;
    }

    @Override
    public List<Race> findScheduledInTimestampDiapasonOnRacecourseCount(int racecourseId, Timestamp begin, Timestamp end) {
        return null;
    }

    @Override
    public List<Race> findFinishedInTimestampDiapasonOnRacecourse(int racecourseId, Timestamp begin, Timestamp end, int offset, int limit) {
        return null;
    }

    @Override
    public List<Race> findFinishedInTimestampDiapasonOnRacecourseCount(int racecourseId, Timestamp begin, Timestamp end) {
        return null;
    }

    @Override
    public List<Race> findByNamePart(String namePart, int offset, int limit) {
        return null;
    }

    @Override
    public int findByNamePartCount(String namePart) {
        return 0;
    }

    @Override
    public void updateStatus(int id, RaceStatus status) {

    }
}
