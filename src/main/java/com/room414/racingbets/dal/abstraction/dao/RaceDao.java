package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.domain.entities.Race;

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
// TODO: change status
public interface RaceDao extends CrudDao<Integer, Race> {
    /**
     * @return List of Races that scheduled on Racecourse with id == id param
     */
    List<Race> findScheduledRacesByRacecourseId(int id, int offset, int limit);

    /**
     * @return Count of Races that scheduled on Racecourse with id == id param
     */
    List<Race> findScheduledRacesByRacecourseIdCount(int id);


    /**
     * @return List of Races that finished on Racecourse with id == id param
     */
    List<Race> findFinishedRacesByRacecourseId(int id, int offset, int limit);

    /**
     * @return Count of Races that finished on Racecourse with id == id param
     */
    List<Race> findFinishedRacesByRacecourseIdCount(int id);


    /**
     * @return List of Races that scheduled on timestamp between begin and end param
     */
    List<Race> findScheduledRacesInTimestampDiapason(Timestamp begin, Timestamp end, int offset, int limit);

    /**
     * @return Count of Races that scheduled on timestamp between begin and end param
     */
    List<Race> findScheduledRacesInTimestampDiapasonCount(Timestamp begin, Timestamp end);


    /**
     * @return List of Races that finished on timestamp between begin and end param
     */
    List<Race> findFinishedRacesInTimestampDiapason(Timestamp begin, Timestamp end, int offset, int limit);

    /**
     * @return Count of Races that finished on timestamp between begin and end param
     */
    List<Race> findFinishedRacesInTimestampDiapasonCount(Timestamp begin, Timestamp end);


    /**
     * @return List of Races that scheduled on timestamp between begin and end param on Racecourse with id == id param
     */
    List<Race> findScheduledRacesInTimestampDiapasonOnRacecourse(
            int racecourseId, Timestamp begin, Timestamp end, int offset, int limit
    );

    /**
     * @return Count of Races that scheduled on timestamp between begin and end param on Racecourse with id == id param
     */
    List<Race> findScheduledRacesInTimestampDiapasonOnRacecourseCount(
            int racecourseId, Timestamp begin, Timestamp end
    );


    /**
     * @return List of Races that finished on timestamp between begin and end param on Racecourse with id == id param
     */
    List<Race> findFinishedRacesInTimestampDiapasonOnRacecourse(
            int racecourseId, Timestamp begin, Timestamp end, int offset, int limit
    );

    /**
     * @return Count of Races that finished on timestamp between begin and end param on Racecourse with id == id param
     */
    List<Race> findFinishedRacesInTimestampDiapasonOnRacecourseCount(
            int racecourseId, Timestamp begin, Timestamp end
    );


    /**
     * @return List of Races which names starts with namePart or empty list if no found.
     */
    List<Race> findByNamePart(String namePart, int offset, int limit);

    /**
     * @return count of Races which names starts with namePart
     */
    int findByNamePartCount(String namePart);
}
