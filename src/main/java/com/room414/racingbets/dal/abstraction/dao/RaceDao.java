package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.RaceStatus;

import java.sql.Timestamp;
import java.util.List;

/**
 * DAO for RaceDto entity
 *
 * @see Race
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public interface RaceDao extends CrudDao<Long, Race> {
    /**
     * @return List of Races that scheduled on RacecourseDto with id == racecourse param
     */
    List<Race> findByRacecourseId(RaceStatus status, long racecourse, int offset, int limit);

    /**
     * @return Count of Races that scheduled on RacecourseDto with id == racecourse param
     */
    int findByRacecourseIdCount(RaceStatus status, long racecourse);


    /**
     * @return List of Races that scheduled on RacecourseDto with name starts with racecourse param
     */
    List<Race> findByRacecourse(RaceStatus status, String racecourse, int offset, int limit);

    /**
     * @return Count of Races that scheduled on RacecourseDto with name starts with racecourse param
     */
    int findByRacecourseCount(RaceStatus status, String racecourse);


    /**
     * @return List of Races that scheduled on timestamp between begin and end param
     */
    List<Race> findInTimestampDiapason(RaceStatus status, Timestamp begin, Timestamp end, int offset, int limit);

    /**
     * @return Count of Races that scheduled on timestamp between begin and end param
     */
    int findInTimestampDiapasonCount(RaceStatus status, Timestamp begin, Timestamp end);


    /**
     * @return List of Races that scheduled on timestamp between begin and end param
     *      on RacecourseDto with id == racecourse param
     */
    List<Race> findInTimestampDiapasonOnRacecourse(
            RaceStatus status, long racecourse, Timestamp begin, Timestamp end, int offset, int limit
    );

    /**
     * @return Count of Races that scheduled on timestamp between begin and end param
     *      on RacecourseDto id == racecourse param
     */
    int findInTimestampDiapasonOnRacecourseCount(
            RaceStatus status, long racecourse, Timestamp begin, Timestamp end
    );


    /**
     * @return List of Races which names starts with namePart or empty list if no found.
     */
    List<Race> findByNamePart(RaceStatus raceStatus, String namePart, int offset, int limit);

    /**
     * @return count of Races which names starts with namePart
     */
    int findByNamePartCount(RaceStatus raceStatus, String namePart);

    boolean updateStatus(long id, RaceStatus status);
}
