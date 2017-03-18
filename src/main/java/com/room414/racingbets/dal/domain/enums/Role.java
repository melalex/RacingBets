package com.room414.racingbets.dal.domain.enums;

import com.room414.racingbets.dal.domain.entities.*;

/**
 * Roles define specific system functionality available to a user.
 *
 * @see ApplicationUser
 * @author Alexander Melashchenko
 * @version 1.0 26 Feb 2017
 */
public enum Role {
    /**
     * Can bet, view history and schedule of races, looking at the statistics of the horses, jockeys and trainers.
     */
    HANDICAPPER("Handicapper"),
    /**
     * Organize races and determine their winners, replenish handicapper's balance, gives winnings.
     *
     * @see ApplicationUser#balance
     */
    BOOKMAKER("Bookmaker"),
    /**
     * Adding an entities (Horse, Owner, Jockey, Trainer, Racecourse) in the webproject.database,
     * adds and removes bookmakers.
     *
     * @see Horse
     * @see Owner
     * @see Jockey
     * @see Trainer
     * @see Racecourse
     * @see Role#BOOKMAKER
     */
    ADMIN("Admin");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Role getRole(String name) {
        for(Role v : values()) {
            if (v.getName().equalsIgnoreCase(name)) {
                return v;
            }
        }
        throw new IllegalArgumentException("There is no Role named " + name);
    }
}
