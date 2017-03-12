package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.exception.DalException;
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
public interface RaceDao extends CrudDao<Long, Race> {
    /**
     * @return List of Races that scheduled on Racecourse with id == racecourse param
     */
    List<Race> findByRacecourseId(RaceStatus status, long racecourse, long offset, long limit) throws DalException;

    /**
     * @return Count of Races that scheduled on Racecourse with id == racecourse param
     */
    long findByRacecourseIdCount(RaceStatus status, long racecourse) throws DalException;


    /**
     * @return List of Races that scheduled on Racecourse with name starts with racecourse param
     */
    List<Race> findByRacecourse(RaceStatus status, String racecourse, long offset, long limit) throws DalException;

    /**
     * @return Count of Races that scheduled on Racecourse with name starts with racecourse param
     */
    long findByRacecourseCount(RaceStatus status, String racecourse) throws DalException;


    /**
     * @return List of Races that scheduled on timestamp between begin and end param
     */
    List<Race> findInTimestampDiapason(RaceStatus status, Timestamp begin, Timestamp end, long offset, long limit);

    /**
     * @return Count of Races that scheduled on timestamp between begin and end param
     */
    long findInTimestampDiapasonCount(RaceStatus status, Timestamp begin, Timestamp end);


    /**
     * @return List of Races that scheduled on timestamp between begin and end param
     *      on Racecourse with id == racecourse param
     */
    List<Race> findInTimestampDiapasonOnRacecourse(
            RaceStatus status, long racecourse, Timestamp begin, Timestamp end, long offset, long limit
    );

    /**
     * @return Count of Races that scheduled on timestamp between begin and end param
     *      on Racecourse id == racecourse param
     */
    long findInTimestampDiapasonOnRacecourseCount(
            RaceStatus status, long racecourse, Timestamp begin, Timestamp end
    );


    /**
     * @return List of Races which names starts with namePart or empty list if no found.
     */
    List<Race> findByNamePart(RaceStatus raceStatus, String namePart, long offset, long limit) throws DalException;

    /**
     * @return count of Races which names starts with namePart
     */
    long findByNamePartCount(RaceStatus raceStatus, String namePart) throws DalException;

    boolean updateStatus(long id, RaceStatus status) throws DalException;
}
