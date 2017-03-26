package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.cache.RaceCache;
import com.room414.racingbets.dal.abstraction.dao.RaceDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.CacheCrudDao;
import com.room414.racingbets.dal.domain.entities.FilterParams;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.RaceStatus;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
// TODO: optimize keys
public class CachedRaceDao extends CacheCrudDao<Race> implements RaceDao {
    private RaceDao dao;
    private RaceCache cache;

    CachedRaceDao(RaceDao dao, RaceCache cache) {
        super(dao, cache);
        this.dao = dao;
        this.cache = cache;
    }

    @Override
    public List<Race> filter(FilterParams params) {
        String key = String.format("filter:%d:%s:%d:%s:%d:%d:%d:%d",
                params.getId(),
                params.getRaceStatus(),
                params.getRacecourseId(),
                params.getName(),
                params.getBegin().getTime(),
                params.getEnd().getTime(),
                params.getLimit(),
                params.getOffset()
        );

        return cache.getManyCached(key, () -> dao.filter(params));
    }

    @Override
    public int count(FilterParams params) {
        String key = String.format("count:%d:%s:%d:%s:%d:%d",
                params.getId(),
                params.getRaceStatus(),
                params.getRacecourseId(),
                params.getName(),
                params.getBegin().getTime(),
                params.getEnd().getTime()
        );

        return cache.getCachedCount(key, () -> dao.count(params));
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
