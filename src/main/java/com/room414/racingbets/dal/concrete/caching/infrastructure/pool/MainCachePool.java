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
    private static final String APPLICATION_USER_NAMESPACE = "application:user";
    private static final String BET_NAMESPACE = "bet";
    private static final String HORSE_NAMESPACE = "horse";
    private static final String JOCKEY_NAMESPACE = "jockey";
    private static final String OWNER_NAMESPACE = "owner";
    private static final String PARTICIPANT_NAMESPACE = "participant";
    private static final String RACE_NAMESPACE = "race";
    private static final String RACECOURSE_NAMESPACE = "racecourse";
    private static final String TRAINER_NAMESPACE = "trainer";
    private static final String ODDS_NAMESPACE = "odds";
    private static final String WHO_AND_WHEN_NAMESPACE = "who:when";

    private CachePool<ApplicationUser> applicationUserCachePool = new CachePool<>(APPLICATION_USER_NAMESPACE);
    private CachePool<Bet> betCachePool = new CachePool<>(BET_NAMESPACE);
    private CachePool<Horse> horseCachePool = new CachePool<>(HORSE_NAMESPACE);
    private CachePool<Jockey> jockeyCachePool = new CachePool<>(JOCKEY_NAMESPACE);
    private CachePool<Owner> ownerCachePool = new CachePool<>(OWNER_NAMESPACE);
    private CachePool<Participant> participantCachePool = new CachePool<>(PARTICIPANT_NAMESPACE);
    private CachePool<Race> raceCachePool = new CachePool<>(RACE_NAMESPACE);
    private CachePool<Racecourse> racecourseCachePool = new CachePool<>(RACECOURSE_NAMESPACE);
    private CachePool<Trainer> trainerCachePool = new CachePool<>(TRAINER_NAMESPACE);
    private CachePool<Odds> oddsCachePool = new CachePool<>(ODDS_NAMESPACE);
    private CachePool<Pair<Participant, Timestamp>> whoAndWhenCachePool = new CachePool<>(WHO_AND_WHEN_NAMESPACE);

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


    public static String getApplicationUserNamespace() {
        return APPLICATION_USER_NAMESPACE;
    }

    public static String getBetNamespace() {
        return BET_NAMESPACE;
    }

    public static String getHorseNamespace() {
        return HORSE_NAMESPACE;
    }

    public static String getJockeyNamespace() {
        return JOCKEY_NAMESPACE;
    }

    public static String getOwnerNamespace() {
        return OWNER_NAMESPACE;
    }

    public static String getParticipantNamespace() {
        return PARTICIPANT_NAMESPACE;
    }

    public static String getRaceNamespace() {
        return RACE_NAMESPACE;
    }

    public static String getRacecourseNamespace() {
        return RACECOURSE_NAMESPACE;
    }

    public static String getTrainerNamespace() {
        return TRAINER_NAMESPACE;
    }

    public static String getOddsNamespace() {
        return ODDS_NAMESPACE;
    }

    public static String getWhoAndWhenNamespace() {
        return WHO_AND_WHEN_NAMESPACE;
    }
}
