package com.room414.racingbets.dal.concrete.caching.infrastructure.lazyload;

import com.room414.racingbets.dal.abstraction.dao.RaceDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
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
    public List<Race> findByRacecourseId(RaceStatus status, long racecourse, long offset, long limit) {
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
    public long findByRacecourseIdCount(RaceStatus status, long racecourse) {
        return getRaceDao().findByRacecourseIdCount(status, racecourse);
    }

    @Override
    public List<Race> findByRacecourse(RaceStatus status, String racecourse, long offset, long limit) {
        return getRaceDao().findByRacecourse(status, racecourse, offset, limit);
    }

    @Override
    public List<Race> findAll(long offset, long limit) {
        return getRaceDao().findAll(offset, limit);
    }

    @Override
    public long count() {
        return getRaceDao().count();
    }

    @Override
    public long findByRacecourseCount(RaceStatus status, String racecourse) {
        return getRaceDao().findByRacecourseCount(status, racecourse);
    }

    @Override
    public long update(Race entity) {
        return getRaceDao().update(entity);
    }

    @Override
    public List<Race> findInTimestampDiapason(RaceStatus status, Timestamp begin, Timestamp end, long offset, long limit) {
        return getRaceDao().findInTimestampDiapason(status, begin, end, offset, limit);
    }

    @Override
    public boolean delete(Long id) {
        return getRaceDao().delete(id);
    }

    @Override
    public long findInTimestampDiapasonCount(RaceStatus status, Timestamp begin, Timestamp end) {
        return getRaceDao().findInTimestampDiapasonCount(status, begin, end);
    }

    @Override
    public List<Race> findInTimestampDiapasonOnRacecourse(RaceStatus status, long racecourse, Timestamp begin, Timestamp end, long offset, long limit) {
        return getRaceDao().findInTimestampDiapasonOnRacecourse(status, racecourse, begin, end, offset, limit);
    }

    @Override
    public long findInTimestampDiapasonOnRacecourseCount(RaceStatus status, long racecourse, Timestamp begin, Timestamp end) {
        return getRaceDao().findInTimestampDiapasonOnRacecourseCount(status, racecourse, begin, end);
    }

    @Override
    public List<Race> findByNamePart(RaceStatus raceStatus, String namePart, long offset, long limit) {
        return getRaceDao().findByNamePart(raceStatus, namePart, offset, limit);
    }

    @Override
    public long findByNamePartCount(RaceStatus raceStatus, String namePart) {
        return getRaceDao().findByNamePartCount(raceStatus, namePart);
    }

    @Override
    public boolean updateStatus(long id, RaceStatus status) {
        return getRaceDao().updateStatus(id, status);
    }
}
