package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.cache.RaceCache;
import com.room414.racingbets.dal.abstraction.dao.RaceDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.CacheCrudDao;
import com.room414.racingbets.dal.concrete.caching.caffeine.caches.CaffeineRaceCache;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.RaceStatus;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CachedRaceDao extends CacheCrudDao<Race> implements RaceDao {
    private RaceDao dao;
    private RaceCache cache;

    CachedRaceDao(RaceDao dao, RaceCache cache) {
        super(dao, cache);
        this.dao = dao;
        this.cache = cache;
    }

    @Override
    public List<Race> findByRacecourseId(RaceStatus status, long racecourse, long offset, long limit) {
        final String key = String.format("find:racecourse:id:%s:%d:%d:%d", status, racecourse, offset, limit);

        return cache.getManyCached(key, () -> dao.findByRacecourseId(status, racecourse, offset, limit));
    }

    @Override
    public long findByRacecourseIdCount(RaceStatus status, long racecourse) throws DalException {
        final String key = String.format("find:racecourse:id:count:%s:%d:", status, racecourse);

        return cache.getCachedCount(key, () -> dao.findByRacecourseIdCount(status, racecourse));
    }

    @Override
    public List<Race> findByRacecourse(RaceStatus status, String racecourse, long offset, long limit) {
        final String key = String.format("find:racecourse:name:%s:%s:%d:%d", status, racecourse, offset, limit);

        return cache.getManyCached(key, () -> dao.findByRacecourse(status, racecourse, offset, limit));
    }

    @Override
    public long findByRacecourseCount(RaceStatus status, String racecourse) {
        final String key = String.format("find:racecourse:name:count:%s:%s:", status, racecourse);

        return cache.getCachedCount(key, () -> dao.findByRacecourseCount(status, racecourse));
    }

    @Override
    public List<Race> findInTimestampDiapason(RaceStatus status, Timestamp begin, Timestamp end, long offset, long limit) {
        final String key = String.format(
                "find:timestamp:%s:%s:%s:%d:%d", status, begin, end, offset, limit
        );

        return cache.getManyCached(key, () -> dao.findInTimestampDiapason(status, begin, end, offset, limit));
    }

    @Override
    public long findInTimestampDiapasonCount(RaceStatus status, Timestamp begin, Timestamp end) {
        final String key = String.format("find:timestamp:count:%s:%s:%s", status, begin, end);

        return cache.getCachedCount(key, () -> dao.findInTimestampDiapasonCount(status, begin, end));
    }

    @Override
    public List<Race> findInTimestampDiapasonOnRacecourse(RaceStatus status, long racecourse, Timestamp begin, Timestamp end, long offset, long limit) {
        final String key = String.format(
                "find:timestamp:%s:%d:%s:%s:%d:%d", status, racecourse, begin, end, offset, limit
        );

        return cache.getManyCached(key, () -> dao.findInTimestampDiapasonOnRacecourse(
                status, racecourse, begin, end, offset, limit
        ));
    }

    @Override
    public long findInTimestampDiapasonOnRacecourseCount(RaceStatus status, long racecourse, Timestamp begin, Timestamp end) {
        final String key = String.format("find:timestamp:count:%s:%d:%s:%s", status, racecourse, begin, end);

        return cache.getCachedCount(key, () -> dao.findInTimestampDiapasonOnRacecourseCount(
                status, racecourse, begin, end
        ));
    }

    @Override
    public List<Race> findByNamePart(RaceStatus raceStatus, String namePart, long offset, long limit) {
        String key = String.format("find:name:%s:%s:%d:%d", raceStatus, namePart, limit, offset);

        return cache.getManyCached(key, () -> dao.findByNamePart(raceStatus, namePart, offset, limit));
    }

    @Override
    public long findByNamePartCount(RaceStatus raceStatus, String namePart) {
        String key = String.format("find:name:count:%s:%s", raceStatus, namePart);

        return cache.getCachedCount(key, () -> dao.findByNamePartCount(raceStatus, namePart));
    }

    @Override
    public boolean updateStatus(long id, RaceStatus status) {
        cache.deleteOneCached(getFindByIdKey(id));
        cache.deleteManyCached();

        if (status == RaceStatus.FINISHED || status == RaceStatus.REJECTED) {
            cache.deleteOddsCache(id);
        }

        return dao.updateStatus(id, status);
    }
}
