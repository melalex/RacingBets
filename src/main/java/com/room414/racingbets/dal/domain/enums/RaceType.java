package com.room414.racingbets.dal.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum that represents type of race.
 *
 * @author Alexander Melashchenko
 * @version 1.0 26 Feb 2017
 */
public enum RaceType {
    /**
     * Where horses gallop directly between two points around a straight or oval track.
     */
    FLAT("flat"),
    /**
     * Where horses race over obstacles.
     */
    JUMP("jump"),
    /**
     * Where horses trot or pace while pulling a driver in a sulky.
     */
    HARNESS("harness");

    private String name;

    RaceType(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static RaceType getRaceType(String name) {
        for(RaceType v : values()) {
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
