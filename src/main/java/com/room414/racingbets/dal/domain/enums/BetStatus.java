package com.room414.racingbets.dal.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Alexander Melashchenko
 * @version 1.0 01 Mar 2017
 */
public enum BetStatus {
    /**
     * The bet is made but the race was not yet.
     */
    SCHEDULED("scheduled"),
    LOSE("lose"),
    WIN("win"),
    /**
     * Bet is returned if the participant was disqualified.
     */
    REJECTED("rejected");

    private String name;

    BetStatus(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static BetStatus getStatus(String name) {
        for(BetStatus v : values()) {
            if (v.getName().equalsIgnoreCase(name)) {
                return v;
            }
        }
        throw new IllegalArgumentException("There is no Gender named " + name);
    }

    @Override
    public String toString() {
        return name;
    }
}
