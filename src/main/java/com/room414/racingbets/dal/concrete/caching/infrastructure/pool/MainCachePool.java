package com.room414.racingbets.dal.concrete.caching.infrastructure.pool;


import com.github.benmanes.caffeine.cache.Cache;
import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;
import com.room414.racingbets.dal.domain.entities.*;

import java.sql.Timestamp;
import java.util.Map;

/**
 * @author Alexander Melashchenko
 * @version 1.0 14 Mar 2017
 */
public class MainCachePool {
    private CachePool<ApplicationUser> applicationUserCachePool = new CachePool<>("application:user");
    private CachePool<Bet> betCachePool = new CachePool<>("bet");
    private CachePool<Horse> horseCachePool = new CachePool<>("horse");
    private CachePool<Jockey> jockeyCachePool = new CachePool<>("jockey");
    private CachePool<Owner> ownerCachePool = new CachePool<>("owner");
    private CachePool<Participant> participantCachePool = new CachePool<>("participant");
    private CachePool<Race> raceCachePool = new CachePool<>("race");
    private CachePool<Racecourse> racecourseCachePool = new CachePool<>("racecourse");
    private CachePool<Trainer> trainerCachePool = new CachePool<>("trainer");
    private CachePool<Odds> oddsCachePool = new CachePool<>("odds");
    private CachePool<Pair<Participant, Timestamp>> whoAndWhenCachePool = new CachePool<>("who:when");

    private Map<String, Cache> cacheByNamespaceMap = createCacheByNamespaceMap();

    private Map<String, Cache> createCacheByNamespaceMap() {
        return CacheByNamespaceMapBuilder.builder()
                .addMap(applicationUserCachePool.getCacheByNamespaceMap())
                .addMap(betCachePool.getCacheByNamespaceMap())
                .addMap(horseCachePool.getCacheByNamespaceMap())
                .addMap(jockeyCachePool.getCacheByNamespaceMap())
                .addMap(ownerCachePool.getCacheByNamespaceMap())
                .addMap(participantCachePool.getCacheByNamespaceMap())
                .addMap(raceCachePool.getCacheByNamespaceMap())
                .addMap(racecourseCachePool.getCacheByNamespaceMap())
                .addMap(trainerCachePool.getCacheByNamespaceMap())
                .addMap(oddsCachePool.getCacheByNamespaceMap())
                .addMap(whoAndWhenCachePool.getCacheByNamespaceMap())
                .build();
    }

    public Map<String, Cache> getCacheByNamespaceMap() {
        return cacheByNamespaceMap;
    }

    public CachePool<ApplicationUser> getApplicationUserCachePool() {
        return applicationUserCachePool;
    }

    public CachePool<Bet> getBetCachePool() {
        return betCachePool;
    }

    public CachePool<Horse> getHorseCachePool() {
        return horseCachePool;
    }

    public CachePool<Jockey> getJockeyCachePool() {
        return jockeyCachePool;
    }

    public CachePool<Owner> getOwnerCachePool() {
        return ownerCachePool;
    }

    public CachePool<Participant> getParticipantCachePool() {
        return participantCachePool;
    }

    public CachePool<Race> getRaceCachePool() {
        return raceCachePool;
    }

    public CachePool<Racecourse> getRacecourseCachePool() {
        return racecourseCachePool;
    }

    public CachePool<Trainer> getTrainerCachePool() {
        return trainerCachePool;
    }

    public CachePool<Odds> getOddsCachePool() {
        return oddsCachePool;
    }

    public CachePool<Pair<Participant, Timestamp>> getWhoAndWhenCachePool() {
        return whoAndWhenCachePool;
    }
}
