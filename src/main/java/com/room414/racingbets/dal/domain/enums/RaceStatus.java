package com.room414.racingbets.dal.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Alexander Melashchenko
 * @version 1.0 01 Mar 2017
 */
public enum  RaceStatus {
    /**
     * The race scheduled (bets are accepted)
     */
    SCHEDULED("scheduled"),
    /**
     * The race started (bets are not accepted)
     */
    RIDING("riding"),
    /**
     * The race is over (bets will not be accepted)
     */
    FINISHED("finished"),
    /**
     * The race is rejected (bets will be returned)
     */
    REJECTED("rejected");

    private String name;

    RaceStatus(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static RaceStatus getRaceStatus(String name) {
        for(RaceStatus v : values()) {
            if (v.getName().equalsIgnoreCase(name)) {
                return v;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
