package com.room414.racingbets.dal.concrete.caching.infrastructure.lazyload;

import com.room414.racingbets.dal.abstraction.dao.RaceDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.RaceStatus;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 13 Mar 2017
 */
public class LazyLoadRaceDao implements RaceDao {
    private UnitOfWork unitOfWork;

    public LazyLoadRaceDao(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    private RaceDao getRaceDao() {
        return unitOfWork.getRaceDao();
    }

    @Override
    public void create(Race entity) {
        getRaceDao().create(entity);
    }

    @Override
    public List<Race> findByRacecourseId(RaceStatus status, long racecourse, int offset, int limit) {
        return getRaceDao().findByRacecourseId(status, racecourse, offset, limit);
    }

    @Override
    public Race find(Long id) {
        return getRaceDao().find(id);
    }

    @Override
    public List<Race> findAll() {
        return getRaceDao().findAll();
    }

    @Override
    public int findByRacecourseIdCount(RaceStatus status, long racecourse) {
        return getRaceDao().findByRacecourseIdCount(status, racecourse);
    }

    @Override
    public List<Race> findByRacecourse(RaceStatus status, String racecourse, int offset, int limit) {
        return getRaceDao().findByRacecourse(status, racecourse, offset, limit);
    }

    @Override
    public List<Race> findAll(int offset, int limit) {
        return getRaceDao().findAll(offset, limit);
    }

    @Override
    public int count() {
        return getRaceDao().count();
    }

    @Override
    public int findByRacecourseCount(RaceStatus status, String racecourse) {
        return getRaceDao().findByRacecourseCount(status, racecourse);
    }

    @Override
    public long update(Race entity) {
        return getRaceDao().update(entity);
    }

    @Override
    public List<Race> findInTimestampDiapason(RaceStatus status, Timestamp begin, Timestamp end, int offset, int limit) {
        return getRaceDao().findInTimestampDiapason(status, begin, end, offset, limit);
    }

    @Override
    public boolean delete(Long id) {
        return getRaceDao().delete(id);
    }

    @Override
    public int findInTimestampDiapasonCount(RaceStatus status, Timestamp begin, Timestamp end) {
        return getRaceDao().findInTimestampDiapasonCount(status, begin, end);
    }

    @Override
    public List<Race> findInTimestampDiapasonOnRacecourse(RaceStatus status, long racecourse, Timestamp begin, Timestamp end, int offset, int limit) {
        return getRaceDao().findInTimestampDiapasonOnRacecourse(status, racecourse, begin, end, offset, limit);
    }

    @Override
    public int findInTimestampDiapasonOnRacecourseCount(RaceStatus status, long racecourse, Timestamp begin, Timestamp end) {
        return getRaceDao().findInTimestampDiapasonOnRacecourseCount(status, racecourse, begin, end);
    }

    @Override
    public List<Race> findByNamePart(RaceStatus raceStatus, String namePart, int offset, int limit) {
        return getRaceDao().findByNamePart(raceStatus, namePart, offset, limit);
    }

    @Override
    public int findByNamePartCount(RaceStatus raceStatus, String namePart) {
        return getRaceDao().findByNamePartCount(raceStatus, namePart);
    }

    @Override
    public boolean updateStatus(long id, RaceStatus status) {
        return getRaceDao().updateStatus(id, status);
    }
}
