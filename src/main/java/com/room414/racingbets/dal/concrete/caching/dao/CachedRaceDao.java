package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.cache.RaceCache;
import com.room414.racingbets.dal.abstraction.dao.RaceDao;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.CacheCrudDao;
import com.room414.racingbets.dal.domain.entities.FilterParams;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.RaceStatus;
import org.jetbrains.annotations.NotNull;

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

    @NotNull
    private String keyFromParams(String root, FilterParams params) {
        StringBuilder builder = new StringBuilder(root);

        if (params.getRaceStatus() != null) {
            builder.append(":s").append(params.getRaceStatus());
        }

        if (params.getId() != null) {
            builder.append(":i").append(params.getId());
        }

        if (params.getRacecourseId() != null) {
            builder.append(":r").append(params.getRacecourseId());
        }

        if (params.getHorseId() != null) {
            builder.append(":h").append(params.getHorseId());
        }

        if (params.getTrainerId() != null) {
            builder.append(":t").append(params.getTrainerId());
        }

        if (params.getJockeyId() != null) {
            builder.append(":j").append(params.getJockeyId());
        }

        if (params.getName() != null) {
            builder.append(":n").append(params.getName());
        }

        if (params.getBegin() != null) {
            builder.append(":b").append(params.getBegin().getTime());
        }

        if (params.getEnd() != null) {
            builder.append(":e").append(params.getEnd().getTime());
        }

        builder.append(":l").append(params.getLimit());
        builder.append(":o").append(params.getOffset());

        return builder.toString();
    }

    @Override
    public List<Race> filter(FilterParams params) {
        return cache.getManyCached(keyFromParams("filter", params), () -> dao.filter(params));
    }

    @Override
    public int count(FilterParams params) {
        return cache.getCachedCount(keyFromParams("count", params), () -> dao.count(params));
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
