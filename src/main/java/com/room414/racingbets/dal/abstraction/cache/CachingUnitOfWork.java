package com.room414.racingbets.dal.abstraction.cache;

/**
 * @author Alexander Melashchenko
 * @version 1.0 16 Mar 2017
 */
public interface CachingUnitOfWork extends AutoCloseable {
    ApplicationUserCache getApplicationUserCache();
    BetCache getBetCache();
    HorseCache getHorseCache();
    JockeyCache getJockeyCache();
    OwnerCache getOwnerCache();
    ParticipantCache getParticipantCache();
    RaceCache getRaceCache();
    RacecourseCache getRacecourseCache();
    TrainerCache getTrainerCache();

    void commit();
    void rollback();
}
