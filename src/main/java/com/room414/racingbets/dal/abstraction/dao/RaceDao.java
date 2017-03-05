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
// TODO: names is to long
public interface RaceDao extends CrudDao<Long, Race> {
    /**
     * @return List of Races that scheduled on Racecourse with id == racecourse param
     */
    List<Race> findScheduledByRacecourseId(long racecourse, long offset, long limit) throws DalException;

    /**
     * @return Count of Races that scheduled on Racecourse with id == racecourse param
     */
    long findScheduledByRacecourseIdCount(long racecourse);


    /**
     * @return List of Races that finished on Racecourse with id == racecourse param
     */
    List<Race> findFinishedByIdRacecourse(long racecourse, long offset, long limit);

    /**
     * @return Count of Races that finished on Racecourse with id == racecourse param
     */
    long findFinishedByIdRacecourseCount(long racecourse);


    /**
     * @return List of Races that scheduled on Racecourse with name starts with racecourse param
     */
    List<Race> findScheduledByRacecourse(String racecourse, long offset, long limit) throws DalException;

    /**
     * @return Count of Races that scheduled on Racecourse with name starts with racecourse param
     */
    long findScheduledByRacecourseCount(String racecourse);


    /**
     * @return List of Races that finished on Racecourse with name starts with racecourse param
     */
    List<Race> findFinishedByRacecourse(String racecourse, long offset, long limit);

    /**
     * @return Count of Races that finished on Racecourse with name starts with racecourse param
     */
    long findFinishedByRacecourseCount(String racecourse);


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
     * @return List of Races that scheduled on timestamp between begin and end param
     *      on Racecourse with id == racecourse param
     */
    List<Race> findScheduledInTimestampDiapasonOnRacecourse(
            long racecourse, Timestamp begin, Timestamp end, long offset, long limit
    );

    /**
     * @return Count of Races that scheduled on timestamp between begin and end param
     *      on Racecourse id == racecourse param
     */
    long findScheduledInTimestampDiapasonOnRacecourseCount(
            long racecourse, Timestamp begin, Timestamp end
    );


    /**
     * @return List of Races that finished on timestamp between begin and end param
     *      on Racecourse with id == racecourse param
     */
    List<Race> findFinishedInTimestampDiapasonOnRacecourse(
            long racecourse, Timestamp begin, Timestamp end, long offset, long limit
    );

    /**
     * @return Count of Races that finished on timestamp between begin and end param
     *      on Racecourse with id == racecourse param
     */
    long findFinishedInTimestampDiapasonOnRacecourseCount(
            long racecourse, Timestamp begin, Timestamp end
    );


    /**
     * @return List of Races which names starts with namePart or empty list if no found.
     */
    List<Race> findByNamePart(String namePart, long offset, long limit) throws DalException;

    /**
     * @return count of Races which names starts with namePart
     */
    long findByNamePartCount(String namePart) throws DalException;

    boolean updateStatus(long id, RaceStatus status) throws DalException;
}
