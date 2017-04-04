package com.room414.racingbets.dal.domain.enums;

/**
 * Enum that represents gender of horse.
 *
 * @author Alexander Melashchenko
 * @version 1.0 26 Feb 2017
 */
public enum Gender {
    MARE("mare"),
    STALLION("stallion");

    private String name;

    Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Gender getGender(String name) {
        for(Gender v : values()) {
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
