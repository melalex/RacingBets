package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.dao.RaceDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.RaceStatus;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CacheRaceDao implements RaceDao {
    private RaceDao raceDao;

    public CacheRaceDao(RaceDao raceDao) {
        this.raceDao = raceDao;
    }

    @Override
    public void create(Race entity) throws DalException {
        raceDao.create(entity);
    }

    @Override
    public List<Race> findByRacecourseId(RaceStatus status, long racecourse, long offset, long limit) throws DalException {
        return raceDao.findByRacecourseId(status, racecourse, offset, limit);
    }

    @Override
    public Race find(Long id) throws DalException {
        return raceDao.find(id);
    }

    @Override
    public List<Race> findAll() throws DalException {
        return raceDao.findAll();
    }

    @Override
    public long findByRacecourseIdCount(RaceStatus status, long racecourse) throws DalException {
        return raceDao.findByRacecourseIdCount(status, racecourse);
    }

    @Override
    public List<Race> findByRacecourse(RaceStatus status, String racecourse, long offset, long limit) throws DalException {
        return raceDao.findByRacecourse(status, racecourse, offset, limit);
    }

    @Override
    public List<Race> findAll(long offset, long limit) throws DalException {
        return raceDao.findAll(offset, limit);
    }

    @Override
    public long count() throws DalException {
        return raceDao.count();
    }

    @Override
    public long findByRacecourseCount(RaceStatus status, String racecourse) throws DalException {
        return raceDao.findByRacecourseCount(status, racecourse);
    }

    @Override
    public long update(Race entity) throws DalException {
        return raceDao.update(entity);
    }

    @Override
    public List<Race> findInTimestampDiapason(RaceStatus status, Timestamp begin, Timestamp end, long offset, long limit) throws DalException {
        return raceDao.findInTimestampDiapason(status, begin, end, offset, limit);
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return raceDao.delete(id);
    }

    @Override
    public long findInTimestampDiapasonCount(RaceStatus status, Timestamp begin, Timestamp end) throws DalException {
        return raceDao.findInTimestampDiapasonCount(status, begin, end);
    }

    @Override
    public List<Race> findInTimestampDiapasonOnRacecourse(RaceStatus status, long racecourse, Timestamp begin, Timestamp end, long offset, long limit) throws DalException {
        return raceDao.findInTimestampDiapasonOnRacecourse(status, racecourse, begin, end, offset, limit);
    }

    @Override
    public long findInTimestampDiapasonOnRacecourseCount(RaceStatus status, long racecourse, Timestamp begin, Timestamp end) throws DalException {
        return raceDao.findInTimestampDiapasonOnRacecourseCount(status, racecourse, begin, end);
    }

    @Override
    public List<Race> findByNamePart(RaceStatus raceStatus, String namePart, long offset, long limit) throws DalException {
        return raceDao.findByNamePart(raceStatus, namePart, offset, limit);
    }

    @Override
    public long findByNamePartCount(RaceStatus raceStatus, String namePart) throws DalException {
        return raceDao.findByNamePartCount(raceStatus, namePart);
    }

    @Override
    public boolean updateStatus(long id, RaceStatus status) throws DalException {
        return raceDao.updateStatus(id, status);
    }
}
