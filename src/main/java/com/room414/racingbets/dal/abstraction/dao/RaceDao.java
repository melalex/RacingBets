package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.RaceStatus;

import java.sql.Timestamp;
import java.util.List;

/**
 * DAO for Race entity
 *
 * @see Race
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
// TODO: names is to long
public interface RaceDao extends CrudDao<Integer, Race> {
    /**
     * @return List of Races that scheduled on Racecourse with id == id param
     */
    List<Race> findScheduledByRacecourseId(long id, long offset, long limit);

    /**
     * @return Count of Races that scheduled on Racecourse with id == id param
     */
    long findScheduledByRacecourseIdCount(long id);


    /**
     * @return List of Races that finished on Racecourse with id == id param
     */
    List<Race> findFinishedByRacecourseId(long id, long offset, long limit);

    /**
     * @return Count of Races that finished on Racecourse with id == id param
     */
    long findFinishedByRacecourseIdCount(long id);


    /**
     * @return List of Races that scheduled on timestamp between begin and end param
     */
    List<Race> findScheduledInTimestampDiapason(Timestamp begin, Timestamp end, long offset, long limit);

    /**
     * @return Count of Races that scheduled on timestamp between begin and end param
     */
    long findScheduledInTimestampDiapasonCount(Timestamp begin, Timestamp end);


    /**
     * @return List of Races that finished on timestamp between begin and end param
     */
    List<Race> findFinishedInTimestampDiapason(Timestamp begin, Timestamp end, long offset, long limit);

    /**
     * @return Count of Races that finished on timestamp between begin and end param
     */
    long findFinishedInTimestampDiapasonCount(Timestamp begin, Timestamp end);


    /**
     * @return List of Races that scheduled on timestamp between begin and end param on Racecourse with id == id param
     */
    List<Race> findScheduledInTimestampDiapasonOnRacecourse(
            long racecourseId, Timestamp begin, Timestamp end, long offset, long limit
    );

    /**
     * @return Count of Races that scheduled on timestamp between begin and end param on Racecourse with id == id param
     */
    long findScheduledInTimestampDiapasonOnRacecourseCount(
            long racecourseId, Timestamp begin, Timestamp end
    );


    /**
     * @return List of Races that finished on timestamp between begin and end param on Racecourse with id == id param
     */
    List<Race> findFinishedInTimestampDiapasonOnRacecourse(
            long racecourseId, Timestamp begin, Timestamp end, long offset, long limit
    );

    /**
     * @return Count of Races that finished on timestamp between begin and end param on Racecourse with id == id param
     */
    long findFinishedInTimestampDiapasonOnRacecourseCount(
            long racecourseId, Timestamp begin, Timestamp end
    );


    /**
     * @return List of Races which names starts with namePart or empty list if no found.
     */
    List<Race> findByNamePart(String namePart, long offset, long limit);

    /**
     * @return count of Races which names starts with namePart
     */
    int findByNamePartCount(String namePart);

    void updateStatus(long id, RaceStatus status);
}
