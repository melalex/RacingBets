package com.room414.racingbets.dal.concrete.caching.infrastructure;

import com.github.benmanes.caffeine.cache.Cache;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class CachePool {
    private Cache<String, ApplicationUser> applicationUserCache;
    private Cache<String, List<ApplicationUser>> applicationUserListCache;


}
