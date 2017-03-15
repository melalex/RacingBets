package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.dao.PersonDao;
import com.room414.racingbets.dal.abstraction.dao.TrainerDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.caching.caffeine.CaffeineCache;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.CachePersonDao;
import com.room414.racingbets.dal.concrete.caching.caffeine.caches.TrainerCache;
import com.room414.racingbets.dal.domain.entities.Trainer;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CachedTrainerDao extends CachePersonDao<Trainer> implements TrainerDao {

    CachedTrainerDao(TrainerDao dao, TrainerCache cache) {
        super(dao, cache);
    }
}
