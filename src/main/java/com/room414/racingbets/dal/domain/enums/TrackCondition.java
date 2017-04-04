package com.room414.racingbets.dal.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents track surface condition type.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Going_(horse_racing)#United_Kingdom_and_Ireland">
 *          Track condition types
 *      </a>
 *
 * @author Alexander Melashchenko
 * @version 1.0 26 Feb 2017
 */
public enum TrackCondition {
    HARD("Hard"),
    FIRM("Firm"),
    GOOD_TO_FIRM("Good to firm"),
    GOOD("Good"),
    GOOD_TO_SOFT("Good to soft"),
    SOFT("Soft"),
    HEAVY("Heavy"),
    FAST("Fast"),
    STANDARD_TO_FAST("Standard to fast"),
    STANDARD("Standard"),
    STANDARD_TO_SLOW("Standard to slow"),
    SLOW("Slow");

    private String name;

    TrackCondition(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static TrackCondition getTrackCondition(String name) {
        if (name == null) {
            return null;
        }

        for(TrackCondition v : values()) {
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
