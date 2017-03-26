package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.domain.enums.RaceStatus;

import java.io.Serializable;
import java.sql.Timestamp;

import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.startsWith;

/**
 * @author Alexander Melashchenko
 * @version 1.0 26 Mar 2017
 */
public class FilterParams implements Serializable {
    private static final long serialVersionUID = 6214553787035632610L;

    private RaceStatus raceStatus;
    private Long id;
    private Long racecourseId;
    private Long horseId;
    private Long trainerId;
    private Long jockeyId;
    private String name;
    private Timestamp begin;
    private Timestamp end;
    private int limit;
    private int offset;


    public String getRaceStatus() {
        if (raceStatus != null) {
            return raceStatus.getName();
        } else {
            return null;
        }
    }

    public void setRaceStatus(RaceStatus raceStatus) {
        this.raceStatus = raceStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (id <= 0) {
            this.id = null;
        } else {
            this.id = id;
        }
    }

    public Long getRacecourseId() {
        return racecourseId;
    }

    public void setRacecourseId(Long racecourseId) {
        if (racecourseId <= 0) {
            this.racecourseId = null;
        } else {
            this.racecourseId = racecourseId;
        }
    }

    public Long getHorseId() {
        return horseId;
    }

    public void setHorseId(Long horseId) {
        if (horseId <= 0) {
            this.horseId = null;
        } else {
            this.horseId = horseId;
        }
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        if (trainerId <= 0) {
            this.trainerId = null;
        } else {
            this.trainerId = trainerId;
        }
    }

    public Long getJockeyId() {
        return jockeyId;
    }

    public void setJockeyId(Long jockeyId) {
        if (jockeyId <= 0) {
            this.jockeyId = null;
        } else {
            this.jockeyId = jockeyId;
        }
    }

    public String getName() {
        if (name != null) {
            return startsWith(name);
        } else {
            return null;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getBegin() {
        return begin;
    }

    public void setBegin(Timestamp begin) {
        this.begin = begin;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

}
