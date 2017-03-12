package com.room414.racingbets.dal.concrete.caching.proxies;

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

    private RaceDao getRaceDao() throws DalException {
        return unitOfWork.getRaceDao();
    }

    @Override
    public void create(Race entity) throws DalException {
        getRaceDao().create(entity);
    }

    @Override
    public List<Race> findByRacecourseId(RaceStatus status, long racecourse, long offset, long limit) throws DalException {
        return getRaceDao().findByRacecourseId(status, racecourse, offset, limit);
    }

    @Override
    public Race find(Long id) throws DalException {
        return getRaceDao().find(id);
    }

    @Override
    public List<Race> findAll() throws DalException {
        return getRaceDao().findAll();
    }

    @Override
    public long findByRacecourseIdCount(RaceStatus status, long racecourse) throws DalException {
        return getRaceDao().findByRacecourseIdCount(status, racecourse);
    }

    @Override
    public List<Race> findByRacecourse(RaceStatus status, String racecourse, long offset, long limit) throws DalException {
        return getRaceDao().findByRacecourse(status, racecourse, offset, limit);
    }

    @Override
    public List<Race> findAll(long offset, long limit) throws DalException {
        return getRaceDao().findAll(offset, limit);
    }

    @Override
    public long count() throws DalException {
        return getRaceDao().count();
    }

    @Override
    public long findByRacecourseCount(RaceStatus status, String racecourse) throws DalException {
        return getRaceDao().findByRacecourseCount(status, racecourse);
    }

    @Override
    public long update(Race entity) throws DalException {
        return getRaceDao().update(entity);
    }

    @Override
    public List<Race> findInTimestampDiapason(RaceStatus status, Timestamp begin, Timestamp end, long offset, long limit) throws DalException {
        return getRaceDao().findInTimestampDiapason(status, begin, end, offset, limit);
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return getRaceDao().delete(id);
    }

    @Override
    public long findInTimestampDiapasonCount(RaceStatus status, Timestamp begin, Timestamp end) throws DalException {
        return getRaceDao().findInTimestampDiapasonCount(status, begin, end);
    }

    @Override
    public List<Race> findInTimestampDiapasonOnRacecourse(RaceStatus status, long racecourse, Timestamp begin, Timestamp end, long offset, long limit) throws DalException {
        return getRaceDao().findInTimestampDiapasonOnRacecourse(status, racecourse, begin, end, offset, limit);
    }

    @Override
    public long findInTimestampDiapasonOnRacecourseCount(RaceStatus status, long racecourse, Timestamp begin, Timestamp end) throws DalException {
        return getRaceDao().findInTimestampDiapasonOnRacecourseCount(status, racecourse, begin, end);
    }

    @Override
    public List<Race> findByNamePart(RaceStatus raceStatus, String namePart, long offset, long limit) throws DalException {
        return getRaceDao().findByNamePart(raceStatus, namePart, offset, limit);
    }

    @Override
    public long findByNamePartCount(RaceStatus raceStatus, String namePart) throws DalException {
        return getRaceDao().findByNamePartCount(raceStatus, namePart);
    }

    @Override
    public boolean updateStatus(long id, RaceStatus status) throws DalException {
        return getRaceDao().updateStatus(id, status);
    }
}
